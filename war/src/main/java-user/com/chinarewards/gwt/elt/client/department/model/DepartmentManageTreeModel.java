package com.chinarewards.gwt.elt.client.department.model;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.department.plugin.DepartmentConstants;
import com.chinarewards.gwt.elt.client.department.presenter.DepartmentListPresenter.DepartmentListDisplay;
import com.chinarewards.gwt.elt.client.ui.HyperLinkCell;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;

public class DepartmentManageTreeModel implements TreeViewModel {
	private ListDataProvider<DepartmentNode> categoryDataProvider;
	private Cell<DepartmentNode> nodeCell;//格式模版
	final List<DepartmentNode> nodeList;//数据
	DepartmentListDisplay departmentListDisplay;
	String corporationId;
	String departmentIds;

	public DepartmentManageTreeModel(List<DepartmentNode> nodeList,
			String corporationId,DepartmentListDisplay departmentListDisplay) {
		this.corporationId = corporationId;
		this.nodeList = nodeList;
		this.departmentListDisplay=departmentListDisplay;
		
		setDataProvider();

		buildNodeCell();
	}

	private void buildNodeCell() {
		List<HasCell<DepartmentNode, ?>> nodeRow = new ArrayList<HasCell<DepartmentNode, ?>>();
		nodeRow = buildDataRow(nodeRow);

		nodeCell = new CompositeCell<DepartmentNode>(nodeRow) {
			@Override
			public void render(Context context, DepartmentNode value,
					SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<table><tbody><tr>");
				super.render(context, value, sb);
				sb.appendHtmlConstant("</tr></tbody></table>");
			}

			@Override
			protected Element getContainerElement(Element parent) {
				return parent.getFirstChildElement().getFirstChildElement()
						.getFirstChildElement();
			}

			@Override
			protected <X> void render(Context context, DepartmentNode value,
					SafeHtmlBuilder sb, HasCell<DepartmentNode, X> hasCell) {
				Cell<X> cell = hasCell.getCell();
				sb.appendHtmlConstant("<td>");

				cell.render(context, hasCell.getValue(value), sb);
				sb.appendHtmlConstant("</td>");
			}
		};
		
	}

	/**
	 * 构造数据行
	 * */
	private List<HasCell<DepartmentNode, ?>> buildDataRow(
			List<HasCell<DepartmentNode, ?>> nodeRow) {

		// 选择框
		nodeRow.add(new HasCell<DepartmentNode, Boolean>() {
			private CheckboxCell cell = new CheckboxCell(true, false);

			public Cell<Boolean> getCell() {
				return cell;
			}

			public FieldUpdater<DepartmentNode, Boolean> getFieldUpdater() {
				return new FieldUpdater<DepartmentNode, Boolean>() {
					@Override
					public void update(int index, DepartmentNode object,
							Boolean checked) {
						// Window.alert(object.getDepartmentId()+"--"+object.getDepartmentName());
						
						System.out.println("======treeMode check event:"+cell.handlesSelection());
						
						departmentIds=departmentListDisplay.getCurrentDepartmentId().getValue();
						departmentIds = updateDepartmentIdsAsChecked(object,
								departmentIds,checked);
			
						departmentListDisplay.getCurrentDepartmentId().setValue(departmentIds);
						
						if(!StringUtil.isEmpty(departmentIds)){	
							String defaultBtnClassName=departmentListDisplay.getDefaultBtnClassName();
							
							departmentListDisplay.getAddSameLevelBtn().setEnabled(true);
							
							departmentListDisplay.getAddChildBtn().setEnabled(true);
							departmentListDisplay.getAddChildBtn().getElement().setClassName("gwt-Button");
							departmentListDisplay.getAddChildBtn().getElement().getParentElement().setClassName(defaultBtnClassName);							
							
							departmentListDisplay.getDeleteBtn().setEnabled(true);
							departmentListDisplay.getDeleteBtn().getElement().setClassName("gwt-Button");
							departmentListDisplay.getDeleteBtn().getElement().getParentElement().setClassName(defaultBtnClassName);	
							
							departmentListDisplay.getEditBtn().setEnabled(true);
							departmentListDisplay.getEditBtn().getElement().setClassName("gwt-Button");
							departmentListDisplay.getEditBtn().getElement().getParentElement().setClassName(defaultBtnClassName);	
						}else{
//							departmentListDisplay.getAddSameLevelBtn().setEnabled(false);
//							departmentListDisplay.getAddSameLevelBtn().getElement().setClassName("gwt-Button[disabled]");		
//							departmentListDisplay.getAddSameLevelBtn().getElement().getParentElement().setClassName("gwt-Button[disabled]");
							
							departmentListDisplay.getAddChildBtn().setEnabled(false);
							departmentListDisplay.getAddChildBtn().getElement().setClassName("gwt-Button[disabled]");		
							departmentListDisplay.getAddChildBtn().getElement().getParentElement().setClassName("gwt-Button[disabled]");
							
							departmentListDisplay.getDeleteBtn().setEnabled(false);
							departmentListDisplay.getDeleteBtn().getElement().setClassName("gwt-Button[disabled]");		
							departmentListDisplay.getDeleteBtn().getElement().getParentElement().setClassName("gwt-Button[disabled]");
							
							departmentListDisplay.getEditBtn().setEnabled(false);
							departmentListDisplay.getEditBtn().getElement().setClassName("gwt-Button[disabled]");		
							departmentListDisplay.getEditBtn().getElement().getParentElement().setClassName("gwt-Button[disabled]");
						}
						
					}
				};
			}

			public Boolean getValue(DepartmentNode object) {
//				return isChecked(object, departmentIds);
				return false;
			}
		});

		// 部门名称
		nodeRow.add(new HasCell<DepartmentNode, String>() {
			private HyperLinkCell cell = new HyperLinkCell();

			public Cell<String> getCell() {
				return cell;
			}

			public FieldUpdater<DepartmentNode, String> getFieldUpdater() {
				return new FieldUpdater<DepartmentNode, String>() {
					@Override
					public void update(int index, DepartmentNode object,
							String value) {
//						Window.alert(object.getDepartmentId() + "--- 部门名称");
						DepartmentClient client = new DepartmentClient();
						client.setId(object.getDepartmentId());
						client.setThisAction(DepartmentConstants.ACTION_DEPARTMENT_EDIT_CORP);
						openEditPage(client);
					}
				};
			}

			public String getValue(DepartmentNode object) {
				return object.getDepartmentName();
			}
		});

		

		return nodeRow;
	}

	public <T> NodeInfo<?> getNodeInfo(T node) {
		if (node == null) {
			return new DefaultNodeInfo<DepartmentNode>(categoryDataProvider,
					nodeCell);
		} else if (node instanceof DepartmentNode) {
			DepartmentNode departmentNode = (DepartmentNode) node;
			List<DepartmentNode> nodeList = new ArrayList<DepartmentNode>();

			for (DepartmentNode tempNode : this.nodeList) {
				if (departmentNode.getDepartmentId().equals(
						tempNode.getParentId()))
					nodeList.add(tempNode);
			}
			ListDataProvider<DepartmentNode> dataProvider = new ListDataProvider<DepartmentNode>(
					nodeList);
			
			return new DefaultNodeInfo<DepartmentNode>(dataProvider, nodeCell);

		}
		String type = node.getClass().getName();
		throw new IllegalArgumentException("Unsupported object type: " + type);
	}

	public boolean isLeaf(Object value) {
		if (value instanceof DepartmentNode) {
			return ((DepartmentNode) value).isLeaf();
		} else {
			return false;
		}
	}
	
	private String updateDepartmentIdsAsChecked(DepartmentNode node,
			String departmentIds,boolean isChecked) {
		if (node != null) {
			String thisId = node.getDepartmentId();

			if (isChecked) {				
				departmentIds = StringUtil.appendString(departmentIds, thisId,
						",");
			} else {
				departmentIds = StringUtil.removeCellString(departmentIds,
						thisId);
			}
		}
		System.out.println("==========update selected Ids:"+departmentIds);
		return departmentIds;
	}

	private void setDataProvider() {
		// 一级部门加载
		if (nodeList != null) {
			categoryDataProvider = new ListDataProvider<DepartmentNode>();
			List<DepartmentNode> categoryList = categoryDataProvider.getList();
			// 顶级
			DepartmentNode rootDepartmentNode = null;
			for (DepartmentNode tempNode : nodeList) {
				if (("ROOT_DEPT" + corporationId).equals(tempNode
						.getDepartmentName()))
					rootDepartmentNode = tempNode;
			}
			// 一级部门
			for (DepartmentNode tempNode : nodeList) {

				if (rootDepartmentNode != null) {

				if (rootDepartmentNode.getDepartmentId().equals(
						tempNode.getParentId()))
					categoryList.add(tempNode);
				}
			}
		} else {
			categoryDataProvider = new ListDataProvider<DepartmentNode>();
			// 无数据时
		}
	}

	private void openEditPage(DepartmentClient client) {
		Platform.getInstance()
				.getEditorRegistry()
				.openEditor(DepartmentConstants.EDITOR_DEPARTMENT_EDIT,
						"EDITOR_DEPARTMENT_EDIT", client);
	}
}

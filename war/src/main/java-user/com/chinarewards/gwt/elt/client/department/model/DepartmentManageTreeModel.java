package com.chinarewards.gwt.elt.client.department.model;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.department.plugin.DepartmentConstants;
import com.chinarewards.gwt.elt.client.department.plugin.DepartmentListConstants;
import com.chinarewards.gwt.elt.client.ui.HyperLinkCell;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.TreeViewModel;

/**
 * 
 */
public class DepartmentManageTreeModel implements TreeViewModel {
	private ListDataProvider<DepartmentNode> categoryDataProvider;
	private Cell<DepartmentNode> nodeCell;
	final List<DepartmentNode> categoryList;

	String corporationId;
	String departmentIds;

	private final DefaultSelectionEventManager<DepartmentNode> selectionManager = DefaultSelectionEventManager
			.createCheckboxManager();
	private SelectionModel<DepartmentNode> selectionModel;

	// public DepartmentManageTreeModel(List<DepartmentNode> categoryList,
	// String corporationId) {
	// this.corporationId = corporationId;
	// this.categoryList = categoryList;
	//
	// setDataProvider();
	//
	// buildNodeCell();
	// }

	public DepartmentManageTreeModel(List<DepartmentNode> categoryList,
			String corporationId,String departmentIds, SelectionModel<DepartmentNode> selectionModel) {
		this.corporationId = corporationId;
		this.departmentIds=departmentIds;
		this.categoryList = categoryList;

		setDataProvider();

		buildNodeCell();

		this.selectionModel = selectionModel;
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

	// 如果 node.getId 在ids中存在，则返回true
	private Boolean isChecked(DepartmentNode node, String departmentIds) {
		if (node != null) {
			String thisId = node.getDepartmentId();
			boolean isExists = StringUtil.containsExistString(departmentIds,
					thisId);

			return isExists;
		}

		return false;
	}

	// currentId存在 则删除， 无 则加入
	private String updateDepartmentIdsAsChecked(DepartmentNode node,
			String departmentIds) {
		if (node != null) {
			String thisId = node.getDepartmentId();
			boolean isExists = StringUtil.containsExistString(departmentIds,
					thisId);

			if (isExists) {
				departmentIds=StringUtil.removeCellString(departmentIds,thisId);
			}else{
				departmentIds=StringUtil.appendString(departmentIds, thisId, ",");
			}
		}

		return departmentIds;
	}

	/**
	 * 构造数据行
	 * */
	private List<HasCell<DepartmentNode, ?>> buildDataRow(
			List<HasCell<DepartmentNode, ?>> row) {

		// 选择框
		row.add(new HasCell<DepartmentNode, Boolean>() {
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

						departmentIds = updateDepartmentIdsAsChecked(object, departmentIds);

						System.out
								.println("====treeModel check event==departmentIds:"
										+ departmentIds);

						DepartmentClient client = new DepartmentClient();
						// client.setThisAction(DepartmentConstants.ACTION_DEPARTMENT_ADD);
						client.setIds(departmentIds);
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										DepartmentListConstants.EDITOR_DEPARTMENTLIST_SEARCH,
										"EDITOR_DEPARTMENTLIST_SEARCH", client);
					}
				};
			}

			public Boolean getValue(DepartmentNode object) {
				return isChecked(object, departmentIds);
			}
		});

		// 部门名称
		row.add(new HasCell<DepartmentNode, String>() {
			private HyperLinkCell cell = new HyperLinkCell();

			public Cell<String> getCell() {
				return cell;
			}

			public FieldUpdater<DepartmentNode, String> getFieldUpdater() {
				return new FieldUpdater<DepartmentNode, String>() {
					@Override
					public void update(int index, DepartmentNode object,
							String value) {
						Window.alert(object.getDepartmentId() + "--- 部门名称");
					}
				};
			}

			public String getValue(DepartmentNode object) {
				return object.getDepartmentName();
			}
		});

		// 操作
		row.add(new HasCell<DepartmentNode, String>() {
			private HyperLinkCell cell = new HyperLinkCell();

			public Cell<String> getCell() {
				return cell;
			}

			public FieldUpdater<DepartmentNode, String> getFieldUpdater() {
				return new FieldUpdater<DepartmentNode, String>() {
					@Override
					public void update(int index, DepartmentNode object,
							String value) {
						DepartmentClient client = new DepartmentClient();
						client.setId(object.getDepartmentId());
						client.setThisAction(DepartmentConstants.ACTION_DEPARTMENT_EDIT);
						openEditPage(client);
					}
				};
			}

			public String getValue(DepartmentNode object) {
				return "操作";
			}
		});

		return row;
	}

	public <T> NodeInfo<?> getNodeInfo(T node) {
		if (node == null) {
			return new DefaultNodeInfo<DepartmentNode>(categoryDataProvider,
					nodeCell);
		} else if (node instanceof DepartmentNode) {
			DepartmentNode departmentNode = (DepartmentNode) node;
			List<DepartmentNode> nodeList = new ArrayList<DepartmentNode>();

			for (DepartmentNode tempNode : this.categoryList) {
				if (departmentNode.getDepartmentId().equals(
						tempNode.getParentId()))
					nodeList.add(tempNode);
			}
			ListDataProvider<DepartmentNode> dataProvider = new ListDataProvider<DepartmentNode>(
					nodeList);
			return new DefaultNodeInfo<DepartmentNode>(dataProvider,
					new DepartmentChildNode(), selectionModel,
					selectionManager, null);//
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

	private void setDataProvider() {
		// 一级部门加载
		if (this.categoryList != null) {
			categoryDataProvider = new ListDataProvider<DepartmentNode>();
			List<DepartmentNode> categoryListx = categoryDataProvider.getList();
			// 顶级
			DepartmentNode rootDepartmentNode = null;
			for (DepartmentNode tempNode : this.categoryList) {
				if (("ROOT_DEPT" + corporationId).equals(tempNode
						.getDepartmentName()))
					rootDepartmentNode = tempNode;
			}
			// 一级部门
			for (DepartmentNode tempNode : this.categoryList) {
				if (rootDepartmentNode.getDepartmentId().equals(
						tempNode.getParentId()))
					categoryListx.add(tempNode);
			}
		} else {
			categoryDataProvider = new ListDataProvider<DepartmentNode>();
			// 无数据时
		}
	}

	private static class DepartmentChildNode extends
			AbstractCell<DepartmentNode> {
		public DepartmentChildNode() {
		}

		@Override
		public void render(Context context, DepartmentNode node,
				SafeHtmlBuilder sb) {
			if (node != null) {
				sb.appendHtmlConstant("<table><tr>");
				sb.appendHtmlConstant("<td>" + node.getDepartmentName()
						+ "</td>");
				sb.appendHtmlConstant("<td>" + "操作child" + "</td>");

				sb.appendHtmlConstant("</tr></table>");
			}
		}
	}
	
	private void openEditPage(DepartmentClient client) {
		Platform.getInstance()
				.getEditorRegistry().openEditor(DepartmentConstants.EDITOR_DEPARTMENT_EDIT, "EDITOR_DEPARTMENT_EDIT", client);
	}
}
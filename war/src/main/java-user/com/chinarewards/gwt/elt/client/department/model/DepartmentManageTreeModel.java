package com.chinarewards.gwt.elt.client.department.model;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.department.plugin.DepartmentConstants;
import com.chinarewards.gwt.elt.client.rewards.model.DepartmentClient;
import com.chinarewards.gwt.elt.client.ui.HyperLinkCell;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;

/**
 * 
 */
public class DepartmentManageTreeModel implements TreeViewModel {
	private ListDataProvider<DepartmentNode> categoryDataProvider;
	private Cell<DepartmentNode> nodeCell;
	String corporationId;
	final List<DepartmentNode> categoryList;

	public DepartmentManageTreeModel(List<DepartmentNode> categoryList,
			String corporationId) {
		this.corporationId = corporationId;
		this.categoryList = categoryList;

		setDataProvider();
		
		List<HasCell<DepartmentNode, ?>> nodeRow = new ArrayList<HasCell<DepartmentNode, ?>>();
		nodeRow=buildDataRow(nodeRow);

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
			List<HasCell<DepartmentNode, ?>> row) {
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
						Window.alert(object.getDepartmentId());
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
						Window.alert(object.getDepartmentId()+"===333");
						
						DepartmentClient client = new DepartmentClient();
						// client.setThisAction(DepartmentConstants.ACTION_DEPARTMENT_ADD);
						Platform.getInstance()
						.getEditorRegistry()
						.openEditor(
								DepartmentConstants.EDITOR_DEPARTMENT_EDIT,
								DepartmentConstants.ACTION_DEPARTMENT_ADD,
								client);
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
			return new DefaultNodeInfo<DepartmentNode>(dataProvider,new DepartmentChildNode());//
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
			for (DepartmentNode category : this.categoryList) {
				if (("ROOT_DEPT" + corporationId).equals(category
						.getDepartmentName()))
					rootDepartmentNode = category;
			}
			// 一级部门
			for (DepartmentNode category : this.categoryList) {
				if (rootDepartmentNode.getDepartmentId().equals(
						category.getParentId()))
					categoryListx.add(category);
			}
		} else {
			categoryDataProvider = new ListDataProvider<DepartmentNode>();
			// 无数据时
		}
	}

	/**
	 * The cell used to render categories.
	 */
	private static class DepartmentNodeCell extends
			AbstractCell<DepartmentNode> {

		public DepartmentNodeCell() {
		}

		@Override
		public void render(Context context, DepartmentNode node,
				SafeHtmlBuilder sb) {
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
				sb.appendHtmlConstant("<table style='width:100%'><tr><td>");

				sb.appendHtmlConstant("<div style='float: left; width: 28%;'>"
						+ node.getDepartmentName() + "</div>");
				sb.appendHtmlConstant("<div style='float: left; width: 28%;'>"
						+ node.getBudgetpoints() + "</div>");
				sb.appendHtmlConstant("<div style='float: left; width: 28%;'>"
						+ "操作child" + "</div>");
				sb.appendHtmlConstant("<div >" + node.getHasawardedpoints()
						+ "</div>");

				sb.appendHtmlConstant("</div></td></tr></table>");
			}
		}
	}
}
package com.chinarewards.gwt.elt.client.department.model;

import java.util.ArrayList;
import java.util.List;

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
 * The {@link TreeViewModel} used to organize contacts into a hierarchy.
 */
public class DepartmentManageTreeModel implements TreeViewModel {

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
			if (node != null) {
				sb.appendHtmlConstant("<div style='float: left; width: 100%;'>");
				sb.appendHtmlConstant("<div style='float: left; width: 58%;'>"
						+ node.getDepartmentName() + "</div>");
				sb.appendHtmlConstant("<div style='float: left; width: 28%;'>"
						+ node.getBudgetpoints() + "</div>");
				sb.appendHtmlConstant("<div >" + node.getHasawardedpoints()
						+ "</div>");
				sb.appendHtmlConstant("</div>");
			}
		}
	}

	private static class DepartmentNodeCell2 extends
			AbstractCell<DepartmentNode> {
		public DepartmentNodeCell2() {
		}

		@Override
		public void render(Context context, DepartmentNode node,
				SafeHtmlBuilder sb) {
			if (node != null) {
				sb.appendHtmlConstant("<table style='width:100%'><tr><td>");
				sb.appendHtmlConstant("<div style='float: left; width: 46%;'>");

				sb.appendHtmlConstant("<div style='float: left; width: 58%;'>"
						+ node.getDepartmentName() + "</div>");
				sb.appendHtmlConstant("<div style='float: left; width: 28%;'>"
						+ node.getBudgetpoints() + "</div>");
				sb.appendHtmlConstant("<div >" + node.getHasawardedpoints()
						+ "</div>");

				sb.appendHtmlConstant("</div></td></tr></table>");
			}
		}
	}

	/**
	 * The static used in this model.
	 */

	private ListDataProvider<DepartmentNode> categoryDataProvider;
	private Cell<DepartmentNode> contactCell1;
	String corporationId;
	final List<DepartmentNode> categoryList;

	public DepartmentManageTreeModel(List<DepartmentNode> categoryList,
			String corporationId) {
		this.corporationId = corporationId;
		this.categoryList = categoryList;

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
		}

		else {
			categoryDataProvider = new ListDataProvider<DepartmentNode>();
			// 无数据时
		}

		// 合并其他元素启用以下代码
		List<HasCell<DepartmentNode, ?>> hasCells1 = new ArrayList<HasCell<DepartmentNode, ?>>();

		hasCells1.add(new HasCell<DepartmentNode, DepartmentNode>() {

			private DepartmentNodeCell cell = new DepartmentNodeCell();

			public Cell<DepartmentNode> getCell() {
				return cell;
			}

			public FieldUpdater<DepartmentNode, DepartmentNode> getFieldUpdater() {
				return new FieldUpdater<DepartmentNode, DepartmentNode>() {

					@Override
					public void update(int index, DepartmentNode object,
							DepartmentNode value) {
						Window.alert(object.getDepartmentId());

					}
				};
			}

			public DepartmentNode getValue(DepartmentNode object) {
				return object;
			}
		});
/**
		
		hasCells1.add(new HasCell<DepartmentNode, String>() {
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
				return "追加";
			}

		});

		hasCells1.add(new HasCell<DepartmentNode, String>() {

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
				return "颁奖历史";
			}
		});*/

		contactCell1 = new CompositeCell<DepartmentNode>(hasCells1) {
			@Override
			public void render(Context context, DepartmentNode value,
					SafeHtmlBuilder sb) {
				// sb.appendHtmlConstant("<div style='width:100%'>");
				// super.render(context, value, sb);
				// sb.appendHtmlConstant("<div style='clear:both;'></div>");
				// sb.appendHtmlConstant("</div>");

				sb.appendHtmlConstant("<table><tbody><tr>");
				super.render(context, value, sb);
				sb.appendHtmlConstant("</tr></tbody></table>");
			}

			@Override
			protected Element getContainerElement(Element parent) {
				// Return the first TR element in the table.
				return parent.getFirstChildElement().getFirstChildElement()
						.getFirstChildElement();
			}

			@Override
			protected <X> void render(Context context, DepartmentNode value,
					SafeHtmlBuilder sb, HasCell<DepartmentNode, X> hasCell) {
				Cell<X> cell = hasCell.getCell();
				if ("追加".equals(hasCell.getValue(value))
						|| "颁奖历史".equals(hasCell.getValue(value)))
					sb.appendHtmlConstant("<td style='padding-left:50px;'>");
				else
					sb.appendHtmlConstant("<td style='width:67%;'>");

				cell.render(context, hasCell.getValue(value), sb);
				sb.appendHtmlConstant("</td>");

			}
		};

	}

	public <T> NodeInfo<?> getNodeInfo(T node) {
		if (node == null) {
			return new DefaultNodeInfo<DepartmentNode>(categoryDataProvider,
					contactCell1);
		} else if (node instanceof DepartmentNode) {
			DepartmentNode categoryValue = (DepartmentNode) node;
			List<DepartmentNode> orderedCounts = new ArrayList<DepartmentNode>();

			for (DepartmentNode category : this.categoryList) {
				if (categoryValue.getDepartmentId().equals(
						category.getParentId()))
					orderedCounts.add(category);
			}
			ListDataProvider<DepartmentNode> dataProvider = new ListDataProvider<DepartmentNode>(
					orderedCounts);
			return new DefaultNodeInfo<DepartmentNode>(dataProvider,
					new DepartmentNodeCell2());
		}
		// Unhandled type.
		String type = node.getClass().getName();
		throw new IllegalArgumentException("Unsupported object type: " + type);
	}

	public boolean isLeaf(Object value) {
		if (value instanceof DepartmentNode)
			return ((DepartmentNode) value).isLeaf();
		else
			return false;
	}
}
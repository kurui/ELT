package com.chinarewards.gwt.elt.client.integralManagement.model;

import java.util.ArrayList;
import java.util.List;

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
 * The {@link TreeViewModel} used to organize contacts into a hierarchy.
 */
public class ContactTreeViewModel implements TreeViewModel {

	/**
	 * The cell used to render categories.
	 */
	private static class CategoryCell extends AbstractCell<Category> {

		/**
		 * The html of the image used for contacts.
		 */

		public CategoryCell() {

		}

		@Override
		public void render(Context context, Category value, SafeHtmlBuilder sb) {
			if (value != null) {
				sb.appendHtmlConstant("<table style='width:100%'>");
				sb.appendHtmlConstant("<tr>");
				sb.appendHtmlConstant("<td >" + value.getDepartmentName()
						+ "</td>");
				sb.appendHtmlConstant("<td >" + value.getBudgetpoints()
						+ "</td>");
				sb.appendHtmlConstant("<td >" + value.getHasawardedpoints()
						+ "</td>");

				sb.appendHtmlConstant("<tr>");
				sb.appendHtmlConstant("</tr>");
				sb.appendHtmlConstant("</table>");
			}
		}
	}

	/**
	 * The static used in this model.
	 */

	private ListDataProvider<Category> categoryDataProvider;
	private Cell<Category> contactCell1;
	String corporationId;
	final List<Category> categoryList;

	public ContactTreeViewModel(List<Category> categoryList,
			String corporationId) {
		this.corporationId = corporationId;
		this.categoryList = categoryList;

		// 一级部门加载
		if (this.categoryList != null) {

			categoryDataProvider = new ListDataProvider<Category>();
			List<Category> categoryListx = categoryDataProvider.getList();
			// 顶级
			Category rootCategory = null;
			for (Category category : this.categoryList) {
				if (("ROOT_DEPT" + corporationId).equals(category
						.getDepartmentName()))
					rootCategory = category;
			}
			// 一级部门
			for (Category category : this.categoryList) {
				if (rootCategory.getDepartmentId().equals(
						category.getParentId()))
					categoryListx.add(category);
			}
		}

		else {
			categoryDataProvider = new ListDataProvider<Category>();
			// 无数据时

		}

		// 合并其他元素启用以下代码
		List<HasCell<Category, ?>> hasCells1 = new ArrayList<HasCell<Category, ?>>();

		hasCells1.add(new HasCell<Category, Category>() {

			private CategoryCell cell = new CategoryCell();

			public Cell<Category> getCell() {
				return cell;
			}

			public FieldUpdater<Category, Category> getFieldUpdater() {
				return new FieldUpdater<Category, Category>() {

					@Override
					public void update(int index, Category object,
							Category value) {
						Window.alert(object.getDepartmentId());

					}
				};
			}

			public Category getValue(Category object) {
				return object;
			}
		});

		hasCells1.add(new HasCell<Category, String>() {

			private HyperLinkCell cell = new HyperLinkCell();

			public Cell<String> getCell() {
				return cell;
			}

			public FieldUpdater<Category, String> getFieldUpdater() {
				return new FieldUpdater<Category, String>() {

					@Override
					public void update(int index, Category object, String value) {
						Window.alert(object.getDepartmentId());

					}
				};
			}

			public String getValue(Category object) {
				return "追加";
			}

		});

		hasCells1.add(new HasCell<Category, String>() {

			private HyperLinkCell cell = new HyperLinkCell();

			public Cell<String> getCell() {
				return cell;
			}

			public FieldUpdater<Category, String> getFieldUpdater() {
				return new FieldUpdater<Category, String>() {

					@Override
					public void update(int index, Category object, String value) {
						Window.alert(object.getDepartmentId());

					}
				};
			}

			public String getValue(Category object) {
				return "颁奖历史";
			}
		});

		contactCell1 = new CompositeCell<Category>(hasCells1) {
			@Override
			public void render(Context context, Category value,
					SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<table style='width:100%'><tbody><tr>");
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
			protected <X> void render(Context context, Category value,
					SafeHtmlBuilder sb, HasCell<Category, X> hasCell) {
				Cell<X> cell = hasCell.getCell();
				sb.appendHtmlConstant("<td>");
				cell.render(context, hasCell.getValue(value), sb);
				sb.appendHtmlConstant("</td>");

			}
		};

	}

	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {
			return new DefaultNodeInfo<Category>(categoryDataProvider,
					contactCell1);
		} else if (value instanceof Category) {
			Category categoryValue = (Category) value;
			List<Category> orderedCounts = new ArrayList<Category>();

			for (Category category : this.categoryList) {
				if (categoryValue.getDepartmentId().equals(
						category.getParentId()))
					orderedCounts.add(category);
			}
			ListDataProvider<Category> dataProvider = new ListDataProvider<Category>(
					orderedCounts);
			return new DefaultNodeInfo<Category>(dataProvider,
					new CategoryCell());
		}
		// Unhandled type.
		String type = value.getClass().getName();
		throw new IllegalArgumentException("Unsupported object type: " + type);
	}

	public boolean isLeaf(Object value) {
		if (value instanceof Category)
			return ((Category) value).isLeaf();
		else
			return false;
	}
}
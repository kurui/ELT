package com.chinarewards.gwt.elt.client.integralManagement.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
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
		// private final String imageHtml;

		public CategoryCell() {
			// this.imageHtml = AbstractImagePrototype.create(image).getHTML();
		}

		@Override
		public void render(Context context, Category value, SafeHtmlBuilder sb) {
			if (value != null) {
				// sb.appendHtmlConstant(imageHtml).appendEscaped(" ");
				// sb.appendEscaped(value.getDisplayName());

				sb.appendHtmlConstant("<table style='width:100%'>");
				sb.appendHtmlConstant("<tr>");
				sb.appendHtmlConstant("<td >" + value.getDisplayName()
						+ "</td>");
				sb.appendHtmlConstant("<td >" + value.getBudgetpoints()
						+ "</td>");
				sb.appendHtmlConstant("<td >" + value.getHasawardedpoints()
						+ "</td>");
				sb.appendHtmlConstant("<td ><a href='#'>追加</a> <a href='#'>颁奖历史</a></td>");
				sb.appendHtmlConstant("<tr>");
				sb.appendHtmlConstant("</tr>");
				sb.appendHtmlConstant("</table>");
			}
		}
	}

	/**
	 * The Cell used to render a {@link ContactInfo}.
	 */

	static class ContactCell extends AbstractCell<ContactInfo> {

		/**
		 * The html of the image used for contacts.
		 */
		// private final String imageHtml;

		public ContactCell() {
			// this.imageHtml = AbstractImagePrototype.create(image).getHTML();
		}

		@Override
		public void render(Context context, ContactInfo value,
				SafeHtmlBuilder sb) {
			// Value can be null, so do a null check..
			if (value == null) {
				return;
			}
			sb.appendHtmlConstant("<table style='width:100%'>");

			// Add the contact image.
			// sb.appendHtmlConstant("<tr><td rowspan='1'>");
			// sb.appendHtmlConstant(imageHtml);
			// sb.appendHtmlConstant("</td>");

			// Add the name and address.
			sb.appendHtmlConstant("<tr><td>");
			sb.appendEscaped("产品部");
			sb.appendHtmlConstant("</td>");
			sb.appendHtmlConstant("<td>"+value.getBudgetpoints()+"</td>");
			sb.appendHtmlConstant("<td>"+value.getHasawardedpoints()+"</td>");
			sb.appendHtmlConstant("<td ><a href='#'>追加</a> <a href='#'>颁奖历史</a></td>");
			sb.appendHtmlConstant("</tr></table>");
		}
	}

	/**
	 * A Cell used to render the LetterCount.
	 */
	private static class LetterCountCell extends AbstractCell<LetterCount> {

		@Override
		public void render(Context context, LetterCount value,
				SafeHtmlBuilder sb) {
			if (value != null) {
				// sb.appendEscaped(value.firstLetter + " (" + value.count +
				// ")");

				sb.appendHtmlConstant("<table style='width:100%'>");
				sb.appendHtmlConstant("<tr><td>");
				sb.appendEscaped( value.getDepartmentsName() + " ("
						+ value.getCount() + ")");
				sb.appendHtmlConstant("</td>");
				sb.appendHtmlConstant("<td>"+value.getBudgetpoints()+"</td>");
				sb.appendHtmlConstant("<td>"+value.getHasawardedpoints()+"</td>");
				sb.appendHtmlConstant("<td ><a href='#'>追加</a> <a href='#'>颁奖历史</a></td>");
				sb.appendHtmlConstant("</tr></table>");
			}
		}
	}

	/**
	 * The static images used in this model.
	 */
	// private static Images images;

	private ListDataProvider<Category> categoryDataProvider;
//	private Cell<ContactInfo> contactCell;
//	private final DefaultSelectionEventManager<ContactInfo> selectionManager = DefaultSelectionEventManager
//			.createDefaultManager();
//	private final SelectionModel<ContactInfo> selectionModel;
	final List<Category> categoryList;

	public ContactTreeViewModel(List<Category> categoryList) {
//		this.selectionModel = selectionModel;
		this.categoryList = categoryList;

		// Create a data provider that provides categories.

		// List<Category> categoryList = null;//categoryDataProvider.getList();

		// 一级部门加载
		if (this.categoryList != null) {

			categoryDataProvider = new ListDataProvider<Category>();
			List<Category> categoryListx = categoryDataProvider.getList();
			for (Category category : this.categoryList) {
				categoryListx.add(category);
			}

		}
		
		else
		{
		    categoryDataProvider = new ListDataProvider<Category>();
//		    List<Category> categoryListx = categoryDataProvider.getList();
//		    for (Category category : ContactDatabase.get().queryCategories()) {
//		      categoryListx.add(category);
//		    }
		}
		
		//合并其他元素启用以下代码
//		List<HasCell<ContactInfo, ?>> hasCells = new ArrayList<HasCell<ContactInfo, ?>>();
//		hasCells.add(new HasCell<ContactInfo, ContactInfo>() {
//
//			private ContactCell cell = new ContactCell();
//
//			public Cell<ContactInfo> getCell() {
//				return cell;
//			}
//
//			public FieldUpdater<ContactInfo, ContactInfo> getFieldUpdater() {
//				return null;
//			}
//
//			public ContactInfo getValue(ContactInfo object) {
//				return object;
//			}
//		});
//
//		contactCell = new CompositeCell<ContactInfo>(
//				hasCells) {
//			@Override
//			public void render(Context context,
//					ContactInfo value, SafeHtmlBuilder sb) {
//				sb.appendHtmlConstant("<table style='width:100%'><tbody><tr>");
//				super.render(context, value, sb);
//				sb.appendHtmlConstant("</tr></tbody></table>");
//
//			}
//
//			@Override
//			protected Element getContainerElement(
//					Element parent) {
//				// Return the first TR element in the table.
//				return parent.getFirstChildElement()
//						.getFirstChildElement()
//						.getFirstChildElement();
//			}
//
//			@Override
//			protected <X> void render(Context context,
//					ContactInfo value, SafeHtmlBuilder sb,
//					HasCell<ContactInfo, X> hasCell) {
//				Cell<X> cell = hasCell.getCell();
//				sb.appendHtmlConstant("<td>");
//				cell.render(context,
//						hasCell.getValue(value), sb);
//				sb.appendHtmlConstant("</td>");
//
//			}
//		};
		// Construct a composite cell for contacts that includes a checkbox.

	}

	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {
			// Return top level categories.
			return new DefaultNodeInfo<Category>(categoryDataProvider,
					new CategoryCell());
		} else if (value instanceof Category) {
			// Return the first letters of each first name.
//			Category category = (Category) value;
	//		List<ContactInfo> contacts = ContactDatabase.get().queryContactsByCategory(category);
			
			List<LetterCount> orderedCounts = new ArrayList<LetterCount>();
			for (int i=1;i<5;i++) {
				orderedCounts.add(new LetterCount("二级部门"+i,"8000","5000","ID"+i));
			}
			
			
			return new DefaultNodeInfo<LetterCount>(
					new ListDataProvider<LetterCount>(orderedCounts),
					new LetterCountCell());
		} else if (value instanceof LetterCount) {
			// Return the contacts with the specified character and first name.
//			LetterCount count = (LetterCount) value;
//			List<ContactInfo> contacts = ContactDatabase.get()
//					.queryContactsByCategoryAndFirstName(count.category,
//							count.getDepartmentId()+ "");
//			
//			
//			ListDataProvider<ContactInfo> dataProvider = new ListDataProvider<ContactInfo>(
//					contacts, ContactInfo.KEY_PROVIDER);
//			return new DefaultNodeInfo<ContactInfo>(dataProvider, contactCell,
//					selectionModel, selectionManager, null);
			List<ContactInfo> orderedCounts = new ArrayList<ContactInfo>();
			for (int i=1;i<5;i++) {
				orderedCounts.add(new ContactInfo("3级部门"+i,"9600","1000","ID"+i));
			}
			return new DefaultNodeInfo<ContactInfo>(
					new ListDataProvider<ContactInfo>(orderedCounts),
					new ContactCell());
		}

		// Unhandled type.
		String type = value.getClass().getName();
		throw new IllegalArgumentException("Unsupported object type: " + type);
	}

	public boolean isLeaf(Object value) {
		return value instanceof ContactInfo;
	}
}
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
				// sb.appendHtmlConstant("<td ><a href='#'>追加</a> <a href='#'>颁奖历史</a></td>");
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
			sb.appendHtmlConstant("<td>" + value.getBudgetpoints() + "</td>");
			sb.appendHtmlConstant("<td>" + value.getHasawardedpoints()
					+ "</td>");
			// sb.appendHtmlConstant("<td ><a href='#'>追加</a> <a href='#'>颁奖历史</a></td>");
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
				sb.appendEscaped(value.getDepartmentsName() + " ("
						+ value.getCount() + ")");
				sb.appendHtmlConstant("</td>");
				sb.appendHtmlConstant("<td>" + value.getBudgetpoints()
						+ "</td>");
				sb.appendHtmlConstant("<td>" + value.getHasawardedpoints()
						+ "</td>");
				// sb.appendHtmlConstant("<td ><a href='#'>追加</a> <a href='#'>颁奖历史</a></td>");
				sb.appendHtmlConstant("</tr></table>");
			}
		}
	}

	/**
	 * The static  used in this model.
	 */

	private ListDataProvider<Category> categoryDataProvider;
	private Cell<Category> contactCell1;
	private Cell<LetterCount> contactCell2;
	private Cell<ContactInfo> contactCell3;


	final List<Category> categoryList;

	public ContactTreeViewModel(List<Category> categoryList) {

		this.categoryList = categoryList;


		// 一级部门加载
		if (this.categoryList != null) {

			categoryDataProvider = new ListDataProvider<Category>();
			List<Category> categoryListx = categoryDataProvider.getList();
			for (Category category : this.categoryList) {
				categoryListx.add(category);
			}

		}

		else {
			categoryDataProvider = new ListDataProvider<Category>();
			//无数据时

		}

		// 合并其他元素启用以下代码
		List<HasCell<Category, ?>> hasCells1 = new ArrayList<HasCell<Category, ?>>();
		List<HasCell<LetterCount, ?>> hasCells2 = new ArrayList<HasCell<LetterCount, ?>>();
		List<HasCell<ContactInfo, ?>> hasCells3 = new ArrayList<HasCell<ContactInfo, ?>>();
		hasCells1.add(new HasCell<Category, Category>() {

			private CategoryCell cell = new CategoryCell();

			public Cell<Category> getCell() {
				return cell;
			}

			public FieldUpdater<Category, Category> getFieldUpdater() {
				return new FieldUpdater<Category, Category>() {
					
					@Override
					public void update(int index, Category object, Category value) {
						Window.alert(object.getDepartmentId());
						
					}
				};
			}

			public Category getValue(Category object) {
				return object;
			}
		});
		hasCells2.add(new HasCell<LetterCount, LetterCount>() {

			private LetterCountCell cell = new LetterCountCell();

			public Cell<LetterCount> getCell() {
				return cell;
			}

			public FieldUpdater<LetterCount, LetterCount> getFieldUpdater() {
				return new FieldUpdater<LetterCount, LetterCount>() {
					
					@Override
					public void update(int index, LetterCount object, LetterCount value) {
						Window.alert(object.getDepartmentId());
						
					}
				};
			}

			public LetterCount getValue(LetterCount object) {
				return object;
			}
		});
		hasCells3.add(new HasCell<ContactInfo, ContactInfo>() {

			private ContactCell cell = new ContactCell();

			public Cell<ContactInfo> getCell() {
				return cell;
			}

			public FieldUpdater<ContactInfo, ContactInfo> getFieldUpdater() {
				return new FieldUpdater<ContactInfo, ContactInfo>() {
					
					@Override
					public void update(int index, ContactInfo object, ContactInfo value) {
						Window.alert(object.getDepartmentId());
						
					}
				};
			}

			public ContactInfo getValue(ContactInfo object) {
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
				return "颁奖";
			}
			
		});
		hasCells2.add(new HasCell<LetterCount, String>() {

			private HyperLinkCell cell = new HyperLinkCell();

			public Cell<String> getCell() {
				return cell;
			}

			public FieldUpdater<LetterCount, String> getFieldUpdater() {
				return new FieldUpdater<LetterCount, String>() {
					
					@Override
					public void update(int index, LetterCount object, String value) {
						Window.alert(object.getDepartmentId());
						
					}
				};
			}

			public String getValue(LetterCount object) {
				return "颁奖";
			}
		});
		hasCells3.add(new HasCell<ContactInfo, String>() {

			private HyperLinkCell cell = new HyperLinkCell();

			public Cell<String> getCell() {
				return cell;
			}

			public FieldUpdater<ContactInfo, String> getFieldUpdater() {
				return new FieldUpdater<ContactInfo, String>() {
					
					@Override
					public void update(int index, ContactInfo object, String value) {
						Window.alert(object.getDepartmentId());
						
					}
				};
			}

			public String getValue(ContactInfo object) {
				return "颁奖";
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
		hasCells2.add(new HasCell<LetterCount, String>() {

			private HyperLinkCell cell = new HyperLinkCell();

			public Cell<String> getCell() {
				return cell;
			}

			public FieldUpdater<LetterCount, String> getFieldUpdater() {
				return new FieldUpdater<LetterCount, String>() {
					
					@Override
					public void update(int index, LetterCount object, String value) {
						Window.alert(object.getDepartmentId());
						
					}
				};
			}

			public String getValue(LetterCount object) {
				return "颁奖历史";
			}
		});
		hasCells3.add(new HasCell<ContactInfo, String>() {

			private HyperLinkCell cell = new HyperLinkCell();

			public Cell<String> getCell() {
				return cell;
			}

			public FieldUpdater<ContactInfo, String> getFieldUpdater() {
				return new FieldUpdater<ContactInfo, String>() {
					
					@Override
					public void update(int index, ContactInfo object, String value) {
						Window.alert(object.getDepartmentId());
						
					}
				};
			}

			public String getValue(ContactInfo object) {
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
		        return parent.getFirstChildElement().getFirstChildElement().getFirstChildElement();
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
		contactCell2 = new CompositeCell<LetterCount>(hasCells2) {
			@Override
			public void render(Context context, LetterCount value,
					SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<table style='width:100%'><tbody><tr>");
				super.render(context, value, sb);
				sb.appendHtmlConstant("</tr></tbody></table>");

			}
		      @Override
		      protected Element getContainerElement(Element parent) {
		        // Return the first TR element in the table.
		        return parent.getFirstChildElement().getFirstChildElement().getFirstChildElement();
		      }
			@Override
			protected <X> void render(Context context, LetterCount value,
					SafeHtmlBuilder sb, HasCell<LetterCount, X> hasCell) {
				Cell<X> cell = hasCell.getCell();
				sb.appendHtmlConstant("<td>");
				cell.render(context, hasCell.getValue(value), sb);
				sb.appendHtmlConstant("</td>");

			}
		};

		contactCell3 = new CompositeCell<ContactInfo>(hasCells3) {
			@Override
			public void render(Context context, ContactInfo value,
					SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<table style='width:100%'><tbody><tr>");
				super.render(context, value, sb);
				sb.appendHtmlConstant("</tr></tbody></table>");

			}
		      @Override
		      protected Element getContainerElement(Element parent) {
		        // Return the first TR element in the table.
		        return parent.getFirstChildElement().getFirstChildElement().getFirstChildElement();
		      }
			@Override
			protected <X> void render(Context context, ContactInfo value,
					SafeHtmlBuilder sb, HasCell<ContactInfo, X> hasCell) {
				Cell<X> cell = hasCell.getCell();
				sb.appendHtmlConstant("<td>");
				cell.render(context, hasCell.getValue(value), sb);
				sb.appendHtmlConstant("</td>");

			}
		};
		// Construct a composite cell for contacts that includes a checkbox.

	}

	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {
			// Return top level categories.

			return new DefaultNodeInfo<Category>(categoryDataProvider,
					contactCell1);
		} else if (value instanceof Category) {
			// Return the first letters of each first name.
			// Category category = (Category) value;
			// List<ContactInfo> contacts =
			// ContactDatabase.get().queryContactsByCategory(category);

			List<LetterCount> orderedCounts = new ArrayList<LetterCount>();
			for (int i = 1; i < 5; i++) {
				orderedCounts.add(new LetterCount("二级部门" + i, "8000", "5000",
						"ID" + i));
			}


			ListDataProvider<LetterCount> dataProvider = new ListDataProvider<LetterCount>(
					orderedCounts);
			return new DefaultNodeInfo<LetterCount>(dataProvider,contactCell2);

		} else if (value instanceof LetterCount) {
			// Return the contacts with the specified character and first name.
			// LetterCount count = (LetterCount) value;
			// List<ContactInfo> contacts = ContactDatabase.get()
			// .queryContactsByCategoryAndFirstName(count.category,
			// count.getDepartmentId()+ "");
			//
			//
			// ListDataProvider<ContactInfo> dataProvider = new
			// ListDataProvider<ContactInfo>(
			// contacts, ContactInfo.KEY_PROVIDER);
			// return new DefaultNodeInfo<ContactInfo>(dataProvider,
			// contactCell,
			// selectionModel, selectionManager, null);
			List<ContactInfo> contacts = new ArrayList<ContactInfo>();
			for (int i = 1; i < 5; i++) {
				contacts.add(new ContactInfo("3级部门" + i, "9600", "1000",
						"ID" + i));
			}

		      ListDataProvider<ContactInfo> dataProvider = new ListDataProvider<ContactInfo>(
		          contacts);
		  	return new DefaultNodeInfo<ContactInfo>(dataProvider,contactCell3);

		}

		// Unhandled type.
		String type = value.getClass().getName();
		throw new IllegalArgumentException("Unsupported object type: " + type);
	}

	public boolean isLeaf(Object value) {
		return value instanceof ContactInfo;
	}
}
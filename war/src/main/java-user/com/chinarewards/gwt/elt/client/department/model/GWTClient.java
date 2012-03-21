package com.chinarewards.gwt.elt.client.department.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinarewards.gwt.elt.client.department.Treetable.TreeItem;
import com.chinarewards.gwt.elt.client.department.Treetable.TreeTable;
import com.chinarewards.gwt.elt.client.department.Treetable.TreeTableRenderer;
import com.chinarewards.gwt.elt.client.integralManagement.model.Category;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;

public class GWTClient {

	public Panel onModuleLoad() {
		HorizontalPanel p = new HorizontalPanel();
		TreeTable fileTreeTable = createFileTreeTable();
		p.add(fileTreeTable);
		TreeTable treeTable = createToDoTreeTable();
		p.add(treeTable);
		treeTable = createSimpleTreeTable();
		p.add(treeTable);

		// Adds a few items after display
		fileTreeTable.addItem(new File("File E", "1 TB", "Jan 1, 2005"));
		fileTreeTable.getItem(0).addItem(
				new File("File E", "1 TB", "Jan 1, 2005"));

		return p;
	}

	/**
	 * Creates an example tree using the default renderer. Wigdets are rendered,
	 * objects are rendered with toString(), and array values are inserted
	 * across the table.
	 */
	public TreeTable createSimpleTreeTable() {
		TreeTable treeTable = new TreeTable();

		TreeItem item = treeTable.addItem("I'm text");
		item.addItem("I'm <b>HTML</b>");
		item.setState(true);
		item = treeTable.addItem(new CheckBox("I'm a Widget!!!"));
		item.addItem("Child");
		item = treeTable.addItem("Parent");
		item.addItem("Child");
		treeTable.addItem(new Object[] { new CheckBox("I'm in an array"), "1",
				"2", new CheckBox("3") });

		return treeTable;
	}

	/**
	 * Creates an example tree using a custom renderer. File objects are added
	 * as user objects and the renderer displays values.
	 * 
	 * @return
	 */
	public TreeTable createFileTreeTable() {
		TreeTable treeTable = new TreeTable();
		treeTable.setBorderWidth(1);
		treeTable.setCellPadding(3);
		treeTable.setCellSpacing(0);

		TreeItem item = treeTable.addItem(new File("Folder A", "-",
				"Apr 4, 2007"));
		item.addItem(new File("File AA", "128 kb", "Apr 4, 2007"));
		item.addItem(new File("File AB", "64 kb", "Apr 1, 2007"));
		item = treeTable.addItem(new File("Folder B", "-", "Jan 21, 2006"));
		item.addItem(new File("File BA", "256 kb", "Jan 18, 2006"));
		item = item.addItem(new File("Folder BB", "-", "Jan 21, 2006"));
		item.addItem(new File("File BBA", "256 kb", "Jan 18, 2006"));
		item.addItem(new File("File BBB", "256 kb", "Jan 18, 2006"));
		item.addItem(new File("File BBC", "256 kb", "Jan 18, 2006"));
		item.addItem(new File("File BBD", "256 kb", "Jan 21, 2006"));
		treeTable.addItem(new File("File C", "256 kb", "Jan 18, 2006"));
		treeTable.addItem(new File("File D", "256 kb", "Jan 18, 2006"));

		treeTable.setRenderer(new ExampleRenderer());

		return treeTable;
	}
	public TreeTable createDepartmentTreeTable(List<DepartmentNode> nodeList) {
		TreeTable treeTable = new TreeTable();
		treeTable.setBorderWidth(1);
		treeTable.setCellPadding(1);
		treeTable.setCellSpacing(0);
		if(nodeList!=null && nodeList.size()>0)
		{
		Map<String, List<DepartmentNode>> map = new HashMap<String, List<DepartmentNode>>();
		String root = "";
		for (int i = 0; i < nodeList.size(); i++) {
			DepartmentNode reply = nodeList.get(i);
			if (!StringUtil.isEmpty(reply.getParentId())) {
				List<DepartmentNode> parentClient = map	.get(reply.getParentId());
				if (parentClient == null)
					parentClient = new ArrayList<DepartmentNode>();
				parentClient.add(reply);
				map.put(reply.getParentId(), parentClient);
			}
			else
			{
				root=reply.getDepartmentId();
			}
		}
		
		treeTable=initTree(root,map,treeTable,null);

		}
		
	

		treeTable.setRenderer(new ExampleRenderer());

		return treeTable;
	}
	private TreeTable  initTree(String parentId,Map<String, List<DepartmentNode>> map,TreeTable treeTable,TreeItem item)
	{
		 List<DepartmentNode> mapx=map.get(parentId);
		 if(mapx!=null && mapx.size()>0)
		 {
			 for (int i = 0; i < mapx.size(); i++) {
				 
				 if(item==null)
					 initTree(mapx.get(i).getDepartmentId(), map, treeTable, treeTable.addItem(new Filex(""+mapx.get(i).getDepartmentName())));
				
				 else
					 item.addItem(new Filex(""+mapx.get(i).getDepartmentName()));
				 
				 
			}
			
		 }
		 return treeTable;
	}
	/**
	 * Creates an example tree using a custom renderer. ToDo objects are added
	 * as the user objects of TreeItems. The renderer turns them into Widgets.
	 * 
	 * @return
	 */
	public TreeTable createToDoTreeTable() {
		TreeTable treeTable = new TreeTable();
		TreeItem grp1 = treeTable.addItem("Group 1");
		grp1.addItem(new ToDo("Garbage", "3 days", "Matt"));
		grp1.addItem(new ToDo("Dishes", "1 day", "Matt"));
		grp1.addItem(new ToDo("Laundry", "2 days", "Matt"));
		TreeItem grp2 = treeTable.addItem("Group 2");
		grp2.addItem(new ToDo("Task 1", "2 days", "Unassigned"));
		grp2.addItem(new ToDo("Task 2", "3 day", "Unassigned"));
		grp2.addItem(new ToDo("Task 3", "7 days", "Unassigned"));

		treeTable.setRenderer(new ExampleRenderer());

		return treeTable;
	}

	class ExampleRenderer implements TreeTableRenderer {
		public void renderTreeItem(TreeTable table, TreeItem item, int row) {
			Object obj = item.getUserObject();
			if (obj instanceof ToDo) {
				ToDo todo = (ToDo) obj;
				item.setWidget(new CheckBox(todo.name));
				table.setText(row, 1, todo.due);
				table.setText(row, 2, todo.who);
			} else if (obj instanceof File) {
				File f = (File) obj;
				item.setText(f.name);
				table.setText(row, 1, f.size);
				table.setText(row, 2, f.date);
			}else if (obj instanceof Filex) {
				Filex f = (Filex) obj;
				item.setWidget(new CheckBox(f.name));
			}
			else if (obj != null) {
				item.setText(obj.toString());
			}
		}
	}

	public class File {
		public String name;
		public String size;
		public String date;

		public File(String n, String s, String d) {
			name = n;
			size = s;
			date = d;
		}

		public String toString() {
			return name;
		}
	}
	public class Filex {
		public String name;
		
		public Filex(String n) {
			name = n;

		}

		public String toString() {
			return name;
		}
	}
	public class ToDo {
		public String name;
		public String due;
		public String who;

		public ToDo(String n, String d, String w) {
			name = n;
			due = d;
			who = w;
		}

		public String toString() {
			return name;
		}
	}
	
	
	public TreeTable createIntegralTreeTable(List<Category> nodeList) {
		TreeTable treeTable = new TreeTable();
		treeTable.setBorderWidth(1);
		treeTable.setCellPadding(1);
		treeTable.setCellSpacing(0);
		if(nodeList!=null && nodeList.size()>0)
		{
		Map<String, List<Category>> map = new HashMap<String, List<Category>>();
		String root = "";
		for (int i = 0; i < nodeList.size(); i++) {
			Category reply = nodeList.get(i);
			if (!StringUtil.isEmpty(reply.getParentId())) {
				List<Category> parentClient = map	.get(reply.getParentId());
				if (parentClient == null)
					parentClient = new ArrayList<Category>();
				parentClient.add(reply);
				map.put(reply.getParentId(), parentClient);
			}
			else
			{
				root=reply.getDepartmentId();
			}
		}
		
		treeTable=initIntegralTree(root,map,treeTable,null);

		}
		
	

		treeTable.setRenderer(new ExampleRenderer());

		return treeTable;
	}
	private TreeTable  initIntegralTree(String parentId,Map<String, List<Category>> map,TreeTable treeTable,TreeItem item)
	{
		 List<Category> mapx=map.get(parentId);
		 if(mapx!=null && mapx.size()>0)
		 {
			 for (int i = 0; i < mapx.size(); i++) {
				 
				 if(item==null)
					 initIntegralTree(mapx.get(i).getDepartmentId(), map, treeTable, treeTable.addItem(new File(""+mapx.get(i).getDepartmentName(),mapx.get(i).getBudgetpoints(),mapx.get(i).getHasawardedpoints())));
				
				 else
					 item.addItem(new File(""+mapx.get(i).getDepartmentName(),mapx.get(i).getBudgetpoints(),mapx.get(i).getHasawardedpoints()));
				 
				 
			}
			
		 }
		 return treeTable;
	}
}

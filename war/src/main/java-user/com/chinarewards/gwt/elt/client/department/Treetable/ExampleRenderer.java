package com.chinarewards.gwt.elt.client.department.Treetable;

import com.google.gwt.user.client.ui.CheckBox;

public   class ExampleRenderer implements TreeTableRenderer {
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
	      } else if (obj != null) {
	        item.setText(obj.toString());
	      }
	    }
	  }
	  
	   class File {
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
	  
	   class ToDo {
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
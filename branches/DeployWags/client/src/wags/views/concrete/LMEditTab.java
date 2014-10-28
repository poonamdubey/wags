package wags.views.concrete;

import wags.ProxyFacilitator;
import wags.WEStatus;
import wags.Common.Presenter;
import wags.ProxyFramework.AbstractServerCall;
import wags.ProxyFramework.GetLMSubjectsCommand;
import wags.admin.builders.BasicDisplay;
import wags.admin.builders.LMBuildBSTDisplay;
import wags.admin.builders.LMBuildBTDisplay;
import wags.admin.builders.LMBuilder;
import wags.admin.builders.LMBuilderFactory;
import wags.admin.builders.LMGraphsDisplay;
import wags.admin.builders.LMInsertNodeDisplay;
import wags.admin.builders.LMSimplePartitionDisplay;
import wags.admin.builders.LMTraversalDisplay;
import wags.presenters.interfaces.LMEditTabPresenter;
import wags.views.interfaces.LMEditTabView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class LMEditTab extends Composite implements ProxyFacilitator, LMEditTabView{

	private static LMEditTabUiBinder uiBinder = GWT
			.create(LMEditTabUiBinder.class);

	interface LMEditTabUiBinder extends UiBinder<Widget, LMEditTab> {
	}

	@UiField Panel vtDisplayHolder;
	@UiField ListBox listBox;
	
	private LMEditTabPresenter presenter;


	public LMEditTab() {
		initWidget(uiBinder.createAndBindUi(this));
		//Sets the default graph to traversals
		BasicDisplay display = new LMTraversalDisplay();
		LMBuilder builder = LMBuilderFactory.getTraversalBuilder();
		vtDisplayHolder.addStyleName("center");
		display.load(vtDisplayHolder, builder);
		
		AbstractServerCall getLMSubjects = new GetLMSubjectsCommand(this);
		getLMSubjects.sendRequest();

		//Add all items to the list box
		listBox.addItem("Traversals");
		listBox.addItem("Insert Node");
		listBox.addItem("Build BST");
		listBox.addItem("Build BT");
		listBox.addItem("SwagsTestGroup");
		listBox.addItem("Kruskal");
		listBox.addItem("Prims");
		listBox.addItem("Linear");
		listBox.addItem("Quadratic");
		listBox.addItem("Insert");
		listBox.addItem("Delete");
		listBox.addItem("Build");
		listBox.addItem("Heap Sort");
		listBox.addItem("Radix Sort");
		listBox.addItem("Quick Sort");
		//On change it finds which item was selected and loads the graph
		 listBox.addChangeHandler(new ChangeHandler()
		 {
		  @Override
		public void onChange(ChangeEvent event)
		  {
			  int selectedIndex = listBox.getSelectedIndex();
			  if (selectedIndex > -1) 
			  {
				  BasicDisplay display;
				  LMBuilder builder;
				  
				  switch( listBox.getValue(selectedIndex)) {
				  case "Traversals":
					  display = new LMTraversalDisplay();
					  builder = LMBuilderFactory.getTraversalBuilder();
				  	  break;
				  case "Insert Node":
					  display = new LMInsertNodeDisplay();
					  builder = LMBuilderFactory.getInsertNodeBuilder();
				  	  break;
				  case "Kruskal":
					  display = new LMGraphsDisplay(false);
					  builder = LMBuilderFactory.getGraphsBuilder();
				  	  break;
				  case "Prims":
					  display = new LMGraphsDisplay(true);
					  builder = LMBuilderFactory.getGraphsBuilder();
				  	  break;
				  case "Quick Sort":
					  display = new LMSimplePartitionDisplay();
					  builder = LMBuilderFactory.getSimplePartitionBuilder();
				  	  break;
				  case "Build BST":
					  display = new LMBuildBSTDisplay();
					  builder = LMBuilderFactory.getBuildBSTBuilder();
				  	  break;
				  case "Build BT":
					  display = new LMBuildBTDisplay();
					  builder = LMBuilderFactory.getBuildBTBuilder();
				  	  break;
				  default :
					  Window.alert("Not yet implemented");
					  display = null;
					  builder = null; 
			      }
				  display.load(vtDisplayHolder, builder);
		      }
		  }
		 });
		 
	}

	@Override
	public void handleExercises(String[] exercises) {}
	@Override
	public void setExercises(String[] exercises) {}
	@Override
	public void setCallback(String[] exercises, WEStatus status) {}
	@Override
	public void getCallback(String[] exercises, WEStatus status, String request) {}
	public void reviewExercise(String exercise) {}
	public void reviewCallback(String[] data) {}
	@Override
	public void handleSubjects(String[] subjects) {}
	@Override
	public void handleGroups(String[] groups) {}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (LMEditTabPresenter) presenter;
	}

	@Override
	public boolean hasPresenter() {
		return presenter != null;
	}

	@Override
	public Presenter getPresenter() {
		return presenter;
	}

	@Override
	public Panel getvtDisplayHolder() {
		return vtDisplayHolder;
	}

	@Override
	public ListBox getlistBox() {
		return listBox;
	}

}

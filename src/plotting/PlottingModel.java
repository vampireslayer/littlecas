package plotting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import math_elements.*;
import math_elemtents_supp.ViewPort;

public class PlottingModel {
		private final List<Function> funcList = new ArrayList<>();
		private ViewPort viewPort;
		
		public List<Function> getFunctions(){
			return Collections.unmodifiableList(funcList);
		}
		public void clearModel(){
			funcList.clear();			
		}
		public ViewPort getViewPort(){
			return viewPort;
		}
		public void setViewPort(ViewPort viewPort){
			this.viewPort = viewPort;
		}
		public void addFunction(Function function){
			funcList.add(function);
		}
}

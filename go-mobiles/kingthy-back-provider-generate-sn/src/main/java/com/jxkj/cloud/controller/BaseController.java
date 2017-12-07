/*package com.jxkj.cloud.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.com.kingthy.annotation.InitBinder;

import com.jxkj.cloud.util.DoubleEditor;
import com.jxkj.cloud.util.FloatEditor;
import com.jxkj.cloud.util.IntegerEditor;
import com.jxkj.cloud.util.LongEditor;

public class BaseController {
	@InitBinder    
	   protected void initBinder(WebDataBinder binder) {    
	       binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));    
	       binder.registerCustomEditor(int.class, new CustomNumberEditor(int.class, true));    
	       binder.registerCustomEditor(int.class, new IntegerEditor());    
           binder.registerCustomEditor(long.class, new CustomNumberEditor(long.class, true));  
	       binder.registerCustomEditor(long.class, new LongEditor());    
	       binder.registerCustomEditor(double.class, new DoubleEditor());    
	       binder.registerCustomEditor(float.class, new FloatEditor());    
	   }  
}
*/
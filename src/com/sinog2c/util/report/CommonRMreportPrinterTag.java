package com.sinog2c.util.report;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Delphi 统计报表功能
 */
@SuppressWarnings("serial")
public class CommonRMreportPrinterTag extends TagSupport {
	private boolean departBoo = false;
	private boolean doubleDateBoo = false;
	private boolean writeDateBoo = false;
	private boolean multiTypesBoo = false;
	private String mutiParam = "";
	private String objHight = "520";
	
	public CommonRMreportPrinterTag() {

	}

	public int doStartTag() throws JspTagException {
		return EVAL_BODY_INCLUDE;
	}

	@SuppressWarnings("unchecked")
	public int doEndTag() throws JspTagException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		StringBuffer html = new StringBuffer();
		String reportID = (String)request.getAttribute("reportID");
		
		String DelphiOBJHight = (String)request.getAttribute("DelphiOBJHight");
		if(null != DelphiOBJHight && "maxHight".equals(DelphiOBJHight)){
			objHight = "610";
		}else{
			objHight = "520";
		}
		
		//当前加载的模板信息，模板内容String
		String reportName = (String)request.getAttribute("reportName");
		String issave = String.valueOf(request.getAttribute("issave")==null?"":request.getAttribute("issave"));
		html.append("<script language=\"javascript\">\n");
		html.append("function SetData(){}\n");
		html.append("</script>\n");
		
		html.append("<form name=\"frm\" method=\"post\" style=\"margin:0 0 0px 0;\">\n");
		html.append("<div class=\"mini-toolbar\"  style=\"padding:2px;border:0;\">\n");
		
		html.append("<table width=\"100%\" height=\"100%\" style=\"margin:0 0 0px 0\";>\n");
		html.append("<tr>\n");
		html.append("<td  align=\"center\" >\n"+reportName);
		if(issave!=null && "save".equals(issave)){
			//html.append("<td align=\"left\" >\n");
			html.append("<a class=\"mini-button\" iconCls=\"icon-save\" plain=\"true\" onclick=\"saveFile\">保存</a>\n");
			//html.append("</td></tr>\n");
		}
		html.append("</td></tr>\n");
		html.append("<tr>\n");
		html.append("<td  width=\"100%\" height=\"100%\">\n");
		RMEngine engine = (RMEngine)request.getAttribute("engine");
		String mydata = (String)request.getAttribute("mydata");
		html.append(engine.CreateCourtViewer("width='100%' height='100%'",mydata));
		html.append("</td>\n");
		html.append("</tr>\n");
		html.append("</table>\n");
		html.append("<input type=\"hidden\" name=\"reportID\" value=\""+reportID+"\"/>\n");//
		html.append("</div>\n");
	    html.append("</form>\n");
		 try {
	            pageContext.getOut().print(html.toString());
	        } catch (IOException ie) {
	            throw new JspTagException("OptionTag:" + ie.getMessage());
	        }
	    departBoo = false;
	    doubleDateBoo = false;
	    writeDateBoo = false;
	    multiTypesBoo = false;
	    mutiParam = "";
	    objHight = "520";
		return EVAL_PAGE;
	}
}

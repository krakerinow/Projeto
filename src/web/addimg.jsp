<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles" %>
<style>
    body{
    background-color: silver;
    }
</style>
<html>
    <div style="padding-left: 43%; padding-top: 10%">
        <h1>Upload File</h1>
        <form action="UploadServlet" enctype="multipart/form-data" method="post">
            <input type="file" name="filename"/><br>
            <input type="submit" value="upload"/>
        </form>
    </div>
</html>
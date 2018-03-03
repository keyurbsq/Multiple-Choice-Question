<%-- 
    Document   : GradeOneQuestion
    Author     : Keyur Patel
--%>

<%@page import= "QBean.QuestionBean" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id= "QuestionBeanID" class= "QBean.QuestionBean" scope= "session">
</jsp:useBean>

<% QuestionBeanID.setMode(request.getParameter("mode")); %>
<% QuestionBeanID.setChecked(request.getParameterValues("choices")); %>
<% QuestionBeanID.setSelected(request.getParameter("choices")); %>
<% QuestionBeanID.displayPage(); %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="intro6e.css" />
        <link rel="stylesheet" type="text/css" href="intro6eselftest.css" />
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/default.min.css">
        <script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js"></script>
        <script>hljs.initHighlightingOnLoad();</script>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
        <style> 
            #question {font-family: "Courier New", Courier, Verdana, Helvetica, sans-serif; font-size: 100%; 
                       color: #8000f0; color: black; margin-left: 0.5em}
            #questionstatement {font-family: 
                                    Times New Roman, monospace, Courier, Verdana, Helvetica, sans-serif; font-size: 100%; color: #8000f0; 
                                color: black; margin-left:1.8em; margin-top:0.5em; margin-bottom:0.5em; }
            #choices {font-family: Times New Roman, Helvetica, sans-serif; font-size: 100%; 
                      color: #8000f0; color: black; margin-left:25.0pt; margin-left:0.5em; margin-bottom:0.5em; }
            #choicemargin {font-family: Times New Roman, Helvetica, sans-serif; font-size: 100%; }
            #choicestatement {font-family: Times New Roman, Helvetica, sans-serif; font-size: 100%; 
                              color: #8000f0; color: black; margin-left:25.0pt; margin-left:0.5em; margin-bottom:0.5em; }
            .preBlock {margin-left:0px;text-indent:0.0px; font-family:monospace; font-size: 120%}
            .keyword {color: green;}
            .comment {color: darkred;  }
            .literal {color: darkblue}
            .constant {color: teal}
            #h3style {color: white; font-family: Helvetica, sans-serif;  font-size: 100%; border-color: #6193cb; text-align: center;margin-bottom: 0.5em; background-color: #6193cb;}  </style>
       
    </head>
    <body style="margin-left: auto; margin-right: auto; width: 45%">
        <form>
          <div>
                <h3 id="h3style" style="width: 500px auto; max-width: 620px; margin: 0 auto; "> Multiple-Choice Question <jsp:getProperty name="QuestionBeanID" property="title" /> </h3>                
            </div>
            <div style="border: 1px solid orange; padding-top: 0px">
                <p><jsp:getProperty name="QuestionBeanID" property="output" /></p>
            </div>
        </form>
    </body>
</html>

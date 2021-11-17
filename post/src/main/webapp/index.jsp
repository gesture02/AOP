<%@page import="post.Post"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="post.DBConn"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	Connection conn = DBConn.getConnection();
	PreparedStatement pstmt = conn.prepareStatement("select * from post");
	ResultSet rs = pstmt.executeQuery();
	List<Post> posts = new ArrayList<>();
	
	while(rs.next()){
		Post post = new Post(
			rs.getInt("id"),
			rs.getString("title"),
			rs.getString("content")
		);
		
		posts.add(post);
	}
%>
<h1>인덱스 페이지입니다</h1>
<hr />

	<table>
		<tr>
			<th>id</th>
			<th>title</th>
			<th>content</th>
		</tr>
		<%
			for(Post post: posts){
				
		%>
		<tr>
			<td><%=post.getId() %></td>
			<td><%=post.getTitle() %></td>
			<td><%=post.getContent() %></td>
		</tr>
		<%
			}
		%>
	</table>
</body>
</html>
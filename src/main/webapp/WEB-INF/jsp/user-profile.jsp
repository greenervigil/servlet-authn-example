<jsp:include page="./includes/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body id="samples">
  <jsp:include page="./includes/menu.jsp" />
  <div id="content" class="container">
    <div>
      <h2>My Profile</h2>
    </div>
    <table class="table table-striped">
      <thead>
        <tr>
          <th>Key</th>
          <th>Value</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${user.profile}" var="item">
          <tr>
            <td>${item.key}</td>
            <td>${item.value}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</body>

<jsp:include page="./includes/footer.jsp" />
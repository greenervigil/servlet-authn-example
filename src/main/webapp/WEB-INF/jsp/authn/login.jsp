<jsp:include page="../includes/header.jsp" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<body>
  <div class="container">
    <div class="row vertical-offset-100">
      <div class="col-md-4 col-md-offset-4">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">Please sign in</h3>
          </div>
          <div class="panel-body">

            <jsp:include page="../includes/form-errors.jsp" />

            <form method="POST" accept-charset="UTF-8" role="form">
              <fieldset>
                <div class="form-group">
                  <input class="form-control" placeholder="Email" name="username" type="text">
                </div>
                <div class="form-group">
                  <input class="form-control" placeholder="Password" name="password" type="password" value="">
                </div>
                <input class="btn btn-lg btn-success btn-block" type="submit" value="Login">
              </fieldset>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>

<jsp:include page="../includes/footer.jsp" />
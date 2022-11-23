  $(document).ready(function(){
    $("table").after('<div id="nav"></div>');
    var rowPerPage = 10;
    var tr = $("table tbody tr");
    var rowsTotal = tr.length;
    var pagesTotal = Math.ceil(rowsTotal/rowPerPage);
    for (i = 0; i < pagesTotal; i++){
      $("<a href=\"#\"></a>").attr("id", i).text(i + 1).appendTo("#nav");
      // $("#nav").append($("<a href=\"#\"></a>").attr("id", i).text(i + 1));
    }
    
    tr
    	.addClass("overflowHidden")
    	//.slice(0, rowPerPage)
    	.removeClass("overflowHidden");
    
    $("#nav a").click(function(){
      $("#nav a").removeClass("active");
      $(this).addClass("active");
      
      var currPage = $(this).attr("id");
      var startItem = currPage * rowPerPage;
      var endItem = startItem + rowPerPage;
      tr
        .css("opacity","0")
        .addClass("overflowHidden")
        .slice(startItem, endItem)
        .removeClass("overflowHidden")
        .animate({opacity:1}, 300);   
      return false();
    });
    $("#nav a:first").addClass("active");
  });
  
/* Copyright (c) 2022 by Han Changsu (https://codepen.io/hcs1105/pen/dyzBBQ) */
  
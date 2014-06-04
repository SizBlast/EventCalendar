onload = function(){
    $('#sandbox-container input').datepicker({
            format: "dd/mm/yyyy",
            todayBtn: "linked",
            language: "nl-BE",
            orientation: "auto",
            todayHighlight: true,
            startDate: "0d",
            autoclose: true
    });
    updateList();
    $("#btnName").click(listName);
};

function updateList(){
    
    var request = new XMLHttpRequest();
    request.open("GET", "http://localhost:8080/EventCalendar/api/events");
    request.onload = function(){
        if (request.status == 200){
            $("#events").empty();
            var events = JSON.parse(request.responseText);
            for (var i = 0; i<events.length; i++){
                var item = $("<li>");
                if (events[i].name){
                    item.html(events[i].name + " (" + events[i].organizer + ") " + events[i].date + ", " + events[i].place);
                }
                $("#events").append(item);
                item.addClass("list-group-item");
            }
            $("#error").empty();
        }
        else
        {
            $("#error").text("Unable to load user list");
        }
    }
    request.send(null);
}

function listName(){
    var request = new XMLHttpRequest();
    request.open("GET", "http://localhost:8080/EventCalendar/api/events/name?q=" + $("#naam").val());
    request.onload = function(){
        if (request.status == 200){
            $("#events").empty();
            var events = JSON.parse(request.responseText);
            for (var i = 0; i<events.length; i++){
                var item = $("<li>");
                if (events[i].name){
                    item.html(events[i].name + " (" + events[i].organizer + ") " + events[i].date + ", " + events[i].place);
                }
                $("#events").append(item);
                item.addClass("list-group-item");
            }
            $("#error").empty();
        }
        else
        {
            $("#error").text("Unable to load user list");
        }
    }
    request.send(null);
}

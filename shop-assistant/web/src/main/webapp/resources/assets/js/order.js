var url = '/orders/new';

var eans;
var shopName;
var timeSlot;

function changeTitle(content) {
    $('#pageTitle').text(content);
}

$(document).ready(function(){
    $('#neworder').click(function(){
        eans = [];
        $('#productsTable').find('tr').each(function () {
            var row = $(this);
            if($(row.find('input[type="checkbox"]')).length) {
                if (row.find('input[type="checkbox"]').is(':checked')) {
                    eans.push(row.find('td:eq(1)').text());
                }
            }
        });
        if(eans.length > 0) {
            var postData = JSON.stringify(eans);
            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                type: "POST",
                dataType: "json",
                data: postData,
                url: url + "1",
                success: form1,
                error: function(jqXHR, textStatus, errorThrown){

                }
            });
        } else {
            //error
        }
    });
});

function form1(items) {
    changeTitle("Making order 1/4");
    var buttonDiv = $('#neworder').parent()
    $('#neworder').remove();

    var prev = $('<button id="prev" class="btn btn-xs btn-danger">Previous</button>');
    prev.appendTo(buttonDiv);
    var next = $('<button id="next" class="btn btn-xs btn-danger">Next</button>');
    next.appendTo(buttonDiv);

    var thead = '';
    thead += '<tr>';
    thead += '<th> EAN </th>';
    thead += '<th> Name </th>';
    thead += '<th> Brand </th>';
    thead += '<th> Quantity </th>';
    thead += '</tr>';
    $('#productsTable thead').html(thead);

    var tbody = '';
    for (var i = 0; i < items.length; i++) {
        tbody += '<tr id="' + items[i].ean + '">';
        tbody += '<td>' + items[i].ean + '</td>';
        tbody += '<td>' + items[i].name + '</td>';
        tbody += '<td>' + items[i].brand + '</td>';
        tbody += '<td><input type="button" class="fa fa-angle-left" value="&#xf011" onClick="decrement(\'' + i + '\')" /><span>' + items[i].quantity + '</span><input type="button" class="fa fa-angle-right" value="&#xf011" onClick="increment(\'' + i + '\')" /></td>';
        tbody += '</tr>';
    }
    $('#productsTable tbody').html(tbody);

    $('#next').unbind('click');
    $("#next").click(function(){
        var postData = JSON.stringify(items);

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            dataType: "json",
            data: postData,
            url: url + "2",
            success: form2,
            error: function(jqXHR, textStatus, errorThrown){
            }
        });
    });
}

function increment(rowString) {
    var row = parseInt(rowString);
    var span = $('#productsTable tbody').find('tr').eq(row).find('td').eq(3).find('span');
    var quantity = parseInt(span.html());
    span.text((quantity + 1).toString());
}

function decrement(rowString) {
    var row = parseInt(rowString);
    var span = $('#productsTable tbody').find('tr').eq(row).find('td').eq(3).find('span');
    var quantity = parseInt(span.html());
    if (quantity > 0) {
        span.text((quantity - 1).toString());
    }
}

function form2(items) {
    changeTitle("Making order 2/4");

    var thead = '';
    thead += '<tr>';
    thead += '<th></th>'
    thead += '<th> Shop </th>';
    thead += '<th> Address </th>';
    thead += '<th> Products </th>';
    thead += '<th> Price </th>';
    thead += '</tr>';
    $('#productsTable thead').html(thead);

    var tbody = '';
    for (var i = 0; i < items.length; i++) {
        tbody += '<tr id="' + i + '">';
        tbody += '<td>' + '<input type="radio" name="shop" value="' + items[i].name + '"/>' + '</td>';
        tbody += '<td>' + items[i].name + '</td>';
        tbody += '<td>' + items[i].address + '</td>';
        tbody += '<td>' + items[i].products + '</td>';
        tbody += '<td>' + items[i].price + '</td>';
        tbody += '</tr>';
    }
    $('#productsTable tbody').html(tbody);
    $('input[type=radio]:first', '#productsTable tbody').attr('checked', true);

    $('#next').unbind('click');
    $("#next").click(function(){
        shopName = $("input[type='radio'][name='shop']:checked").val();
        var postData = JSON.stringify(shopName);

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            dataType: "json",
            data: postData,
            url: url + "3",
            success: form3,
            error: function(jqXHR, textStatus, errorThrown){
            }
        });
    });
}

function form3(items) {
    changeTitle("Making order 3/4");

    var thead = '';
    thead += '<tr>';
    thead += '<th></th>'
    thead += '<th> Date </th>';
    thead += '<th> From </th>';
    thead += '<th> To </th>';
    thead += '</tr>';
    $('#productsTable thead').html(thead);

    var tbody = '';
    for (var i = 0; i < items.length; i++) {
        tbody += '<tr id="' + i + '">';
        tbody += '<td>' + '<input type="radio" name="timeslot" value="' + i + '"/>' + '</td>';
        tbody += '<td>' + items[i].date + '</td>';
        tbody += '<td>' + items[i].from + '</td>';
        tbody += '<td>' + items[i].to + '</td>';
        tbody += '</tr>';
    }
    $('#productsTable tbody').html(tbody);
    $('input[type=radio]:first', '#productsTable tbody').attr('checked', true);

    $('#next').unbind('click');
    $("#next").click(function(){
        var itemIdx = $("input[type='radio'][name='timeslot']:checked").val();
        timeSlot = items[itemIdx];
        form4();
    });
}

function form4() {
    changeTitle("Making order 4/4");
    $('#productsTable thead').html('');
    $('#productsTable tbody').html('');

    $('#next').unbind('click');
    $("#next").click(function(){
        var order = {eans:eans, shopName:shopName, timeSlot:timeSlot};
        var postData = JSON.stringify(order);

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            dataType: "json",
            data: postData,
            url: url + "5",
            success: orderCreated,
            error: function(jqXHR, textStatus, errorThrown){
            }
        });
    });
}

function orderCreated() {

}
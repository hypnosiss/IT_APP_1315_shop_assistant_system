var url = '/orders/new';

var eans;
var products;
var shop;
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
            var buttonParent = $('#neworder').parent();
            buttonParent.empty();
            var prev = $('<button id="prev" class="btn btn-xs btn-danger">Previous</button>');
            prev.appendTo(buttonParent);
            var next = $('<button id="next" class="btn btn-xs btn-danger">Next</button>');
            next.appendTo(buttonParent);

            var postData = JSON.stringify(eans);
            makeAjaxRequest(url + "1", form1, "POST", postData);
        } else {
            //error
        }
    });
});

function form1(items) {
    changeTitle("Making order 1/4");

    products = items;

    var thead = '';
    thead += '<tr>';
    thead += '<th> EAN </th>';
    thead += '<th> Name </th>';
    thead += '<th> Brand </th>';
    thead += '<th> Quantity </th>';
    thead += '</tr>';
    $('#productsTable').find('thead').html(thead);

    var tbody = '';
    for (var i = 0; i < items.length; i++) {
        tbody += '<tr id="' + items[i].ean + '">';
        tbody += '<td>' + items[i].ean + '</td>';
        tbody += '<td>' + items[i].name + '</td>';
        tbody += '<td>' + items[i].brand + '</td>';
        tbody += '<td><input type="button" class="fa fa-angle-left" value="&#xf011" onClick="decrement(\'' + i + '\')" /><span>' + products[i].quantity + '</span><input type="button" class="fa fa-angle-right" value="&#xf011" onClick="increment(\'' + i + '\')" /></td>';
        tbody += '</tr>';
    }
    $('#productsTable').find('tbody').html(tbody);

    $('#prev').unbind('click');
    $('#prev').click(function(){

    });

    $('#next').unbind('click');
    $("#next").click(function(){
        var postData = JSON.stringify(products);
        makeAjaxRequest(url + "2", form2, "POST", postData);
    });
}

function increment(rowString) {
    var row = parseInt(rowString);
    var span = $('#productsTable').find('tbody').find('tr').eq(row).find('td').eq(3).find('span');
    var quantity = parseInt(span.html());
    products[row].quantity++;
    span.text((quantity + 1).toString());
}

function decrement(rowString) {
    var row = parseInt(rowString);
    var span = $('#productsTable').find('tbody').find('tr').eq(row).find('td').eq(3).find('span');
    var quantity = parseInt(span.html());
    if (quantity > 0) {
        products[row].quantity--;
        span.text((quantity - 1).toString());
    }
}

function form2(items) {
    changeTitle("Making order 2/4");

    var thead = '';
    thead += '<tr>';
    thead += '<th></th>';
    thead += '<th> Shop </th>';
    thead += '<th> Address </th>';
    thead += '<th> Products </th>';
    thead += '<th> Price </th>';
    thead += '</tr>';
    $('#productsTable').find('thead').html(thead);

    var tbody = '';
    for (var i = 0; i < items.length; i++) {
        tbody += '<tr id="' + i + '">';
        tbody += '<td>' + '<input type="radio" name="shop" value="' + i + '"/>' + '</td>';
        tbody += '<td>' + items[i].name + '</td>';
        tbody += '<td>' + items[i].address + '</td>';
        tbody += '<td>' + items[i].products + '</td>';
        tbody += '<td>' + items[i].price + '</td>';
        tbody += '</tr>';
    }
    $('#productsTable').find('tbody').html(tbody);
    $('input[type=radio]:first', '#productsTable tbody').attr('checked', true);

    $('#prev').unbind('click');
    $('#prev').click(function(){
        var postData = JSON.stringify(eans);
        makeAjaxRequest(url + "1", form1, "POST", postData);
    });

    $('#next').unbind('click');
    $("#next").click(function(){
        var shopIdx = $("input[type='radio'][name='shop']:checked").val();
        shop = items[shopIdx];
        var postData = JSON.stringify(shop.shopName);
        makeAjaxRequest(url + "3", form3, "POST", postData);
    });
}

function form3(items) {
    changeTitle("Making order 3/4");

    var div =  $('#summary');
    if(div) {
        div.remove();
    }

    var thead = '';
    thead += '<tr>';
    thead += '<th></th>';
    thead += '<th> Date </th>';
    thead += '<th> From </th>';
    thead += '<th> To </th>';
    thead += '</tr>';
    $('#productsTable').find('thead').html(thead);

    var tbody = '';
    for (var i = 0; i < items.length; i++) {
        tbody += '<tr id="' + i + '">';
        tbody += '<td>' + '<input type="radio" name="timeslot" value="' + i + '"/>' + '</td>';
        tbody += '<td>' + items[i].date + '</td>';
        tbody += '<td>' + items[i].from + '</td>';
        tbody += '<td>' + items[i].to + '</td>';
        tbody += '</tr>';
    }
    $('#productsTable').find('tbody').html(tbody);
    $('input[type=radio]:first', '#productsTable tbody').attr('checked', true);

    $('#prev').unbind('click');
    $('#prev').click(function(){
        var postData = JSON.stringify(products);
        makeAjaxRequest(url + "2", form2, "POST", postData);
    });

    $('#next').unbind('click');
    $("#next").click(function(){
        var itemIdx = $("input[type='radio'][name='timeslot']:checked").val();
        timeSlot = items[itemIdx];
        makeAjaxRequest(url + "4", form4, "GET");
    });
}

function form4(items) {
    changeTitle("Making order 4/4");
    $('#productsTable').find('thead').html('');
    $('#productsTable').find('tbody').html('');

    var parent = $('#productsTable').parent();
    var div =  $('<div id="summary">');
    div.appendTo(parent);

    $('<h4>', {
        text: "Products:"
    }).appendTo(div);
    $.each(products, function(idx, product){
        $('<p>', {
            text:  product.quantity + " of " + product.name
        }).appendTo(div);
    });

    $('<h4>', {
        text: "Shop:"
    }).appendTo(div);
    $('<p>', {
        text: shop.name + ", " + shop.address
    }).appendTo(div);

    $('<h4>', {
        text: "Delivery address:"
    }).appendTo(div);
    $('<p>', {
        text: items.value
    }).appendTo(div);

    $('<h4>', {
        text: "Delivery date and time:"
    }).appendTo(div);
    $('<p>', {
        text: timeSlot.date + " between " +  timeSlot.from + " and " + timeSlot.to
    }).appendTo(div);

    $('#prev').unbind('click');
    $('#prev').click(function(){
        var postData = JSON.stringify(shop.shopName);
        makeAjaxRequest(url + "3", form3, "POST", postData);
    });

    $('#next').unbind('click');
    $("#next").click(function(){
        var order = {eans:eans, shopName:shop.shopName, timeSlot:timeSlot};
        var postData = JSON.stringify(order);
        makeAjaxRequest(url + "/finalize", orderCreated, "POST", postData);
    });
}

function orderCreated() {

}

function makeAjaxRequest(url, callback, type, data) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: type,
        dataType: "json",
        data: data,
        url: url,
        success: callback,
        error: function(jqXHR, textStatus, errorThrown){
            console.log(jqXHR, textStatus, errorThrown);
        }
    });
}
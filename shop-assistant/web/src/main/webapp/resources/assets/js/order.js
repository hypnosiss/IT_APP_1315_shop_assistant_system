var url = '/products/newOrder';

$(document).ready(function(){
    $('#neworder').click(function(){
        var eans = [];
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
                url: url,
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
    var thead = '';
    for (var i = 0; i < items.length; i++) {
        thead += '<tr>';
        thead += '<th> EAN </th>';
        thead += '<th> NAME </th>';
        thead += '<th> BRAND </th>';
        thead += '<th> QUANTITY </th>';
        thead += '</tr>';
    }
    $('#productsTable thead').html(thead);

    var tbody = '';
    for (var i = 0; i < items.length; i++) {
        tbody += '<tr id="' + items.ean + '">';
        tbody += '<td>' + items[i].ean + '</td>';
        tbody += '<td>' + items[i].name + '</td>';
        tbody += '<td>' + items[i].brand + '</td>';
        tbody += '<td>' + items[i].quantity + '</td>';
        tbody += '</tr>';
    }
    $('#productsTable tbody').html(tbody);

}
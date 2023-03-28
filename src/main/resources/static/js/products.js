// $(document).ready(function () {
//     $("#send").click(function () {
//         sendContent();
//     });
// });
//
// function sendContent() {
//     let jsonString = JSON.stringify({
//         'title': $("#title").val(),
//         'price': $("#price").val(),
//         'category': $("#category").val()
//     });
//     $.ajax({
//         url: "/products/add",
//         type: 'POST',
//         contentType: "application/json",
//         dataType: "json",
//         data: jsonString,
//         success: function (jsonString) {
//             let product = JSON.parse(jsonString.body);
//             $("#myTable").append("<tr>" +
//                 "<td>" + product.title + "</td>" +
//                 "<td>" + product.price + "</td>" +
//                 "<td>" + product.category + "</td>" +
//                 "<td><a href='/products/" + product.id + "/bucket'>Add to bucket</a></td>" +
//                 "<td><a href='/products/" + product.id + "/delete'>Delete</a></td>" +
//                 "</tr>");
//         },
//         error: function (e) {
//             console.log(e.message);
//         }
//     })
// }




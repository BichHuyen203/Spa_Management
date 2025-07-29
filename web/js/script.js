/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
// Kích hoạt carousel
$(document).ready(function () {
    $('.carousel').carousel();

    setInterval(function () {
        $('.carousel').carousel('next');
    }, 4000); 
});






document.addEventListener("DOMContentLoaded", function() {
    // Get the height of the header
    var headerHeight = document.querySelector('header').offsetHeight;

    // Set the body's padding-top to match the header's height
    document.body.style.paddingTop = headerHeight + 'px';
});
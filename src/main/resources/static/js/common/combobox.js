/*Dropdown Menu*/
$('.combo-dropdown').click(function () {
    $(this).attr('tabindex', 1).focus();
    $(this).toggleClass('active');
    $(this).find('.combo-dropdown-menu').slideToggle(300);
});
$('.combo-dropdown').focusout(function () {
    $(this).removeClass('active');
    $(this).find('.combo-dropdown-menu').slideUp(300);
});
$('.combo-dropdown .combo-dropdown-menu li').click(function () {
    $(this).parents('.combo-dropdown').find('span').text($(this).text());
    $(this).parents('.combo-dropdown').find('input').attr('value', $(this).attr('id'));
});
/*End Dropdown Menu*/


$('.combo-dropdown-menu li').click(function () {
    var input = '<strong>' + $(this).parents('.combo-dropdown').find('input').val() + '</strong>',
        msg = '<span class="msg">Hidden input value: ';
    $('.msg').html(msg + input + '</span>');
});
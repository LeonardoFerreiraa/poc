$(document).ready(function() {
    $('a[data-method]').click(function (event) {
        event.preventDefault();
        if ($(this).data('confirm')) {
            if (!confirm($(this).data('confirm'))) {
                return false;
            }
        }

        var form = $('<form></form>');
        form.attr('action', $(this).attr('href'));
        form.attr('method', 'POST');

        var field = $('<input></input>');
        field.attr('type', 'hidden');
        field.attr('name', '_method');
        field.attr('value', $(this).data('method'));

        form.append(field);

        $(document.body).append(form);
        form.submit();
        return false;
	});
})

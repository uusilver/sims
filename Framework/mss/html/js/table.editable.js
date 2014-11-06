/**
 * Created with JetBrains WebStorm.
 * User: vali
 * Date: 14-11-4
 * Time: 下午9:50
 * To change this template use File | Settings | File Templates.
 */
(function ($) {

    $.joytech._editable = {

        _editor: null,
        _richeditor: null,
        _currenteditor: null,
        _holder: null,

        _blur: function (event) {
            var pthis = $.joytech._editable;
            if (pthis._holder) {
                var e = $(pthis._holder);
                switch (e.attr("joytech_editable_type")) {
                    case 'text': e.text(pthis._currenteditor.value); break;
                    case 'html': e.html(pthis._currenteditor.value); break;
                    case 'value': e.val(pthis._currenteditor.value); break;
                }
                pthis._holder = null;

                $(pthis._currenteditor).hide();
            }
            return false;
        },

        _click: function (event) {
            var pthis = $.joytech._editable;
            if (pthis._editor == null) {
                $(document.body).append("<input type='text' style='position:absolute;' id='joytech__editable__editor'/>");
                pthis._editor = $('#joytech__editable__editor')[0];
                $(pthis._editor).blur($.joytech._editable._blur).hide();
            }
            if (pthis._richeditor == null) {
                $(document.body).append("<textarea style='position:absolute;' id='joytech__editable__richeditor'/>");
                pthis._richeditor = $('#joytech__editable__richeditor')[0];
                $(pthis._richeditor).blur($.joytech._editable._blur).hide();
            }
            pthis._holder = event.target;
            var e = $(event.target);

            var bricheditor = (e.attr("joytech_editable_type")) == 'html' && (e.attr("joytech_editable_usingricheditor")=='true');
            if (bricheditor) pthis._currenteditor = pthis._richeditor;
            else pthis._currenteditor = pthis._editor;

            var i = $(pthis._currenteditor);

            var value = '';
            switch (e.attr("joytech_editable_type")) {
                case 'text': value = e.text(); break;
                case 'html': value = e.html(); break;
                case 'value': value = e.val(); break;
            }

            if (bricheditor) i.val(value);
            else i.val(value);

            var o = e.offset();
            i.css({
                left: o.left,
                top: o.top,
                width: e.width(),
                height: e.height()
            }).show().focus();
            return false;
        }

    };

    $.fn.editableHTML = function (usingricheditor) {
        this.attr("joytech_editable_type", "html");
        this.attr("joytech_editable_usingricheditor", usingricheditor);
        this.click($.joytech._editable._click);
        return $;
    };

    $.fn.editableText = function () {
        this.attr("joytech_editable_type", "text");
        this.click($.joytech._editable._click);
        return $;
    };

    $.fn.editableValue = function () {
        this.attr("joytech_editable_type", "value");
        this.click($.joytech._editable._click);
        return $;
    };

})(jQuery);

(function($, document, window){
    'use strict';

    function EasyEditor( element, options ){
        this.elem = element;
        options = options || {};
        this.className = options.className || 'easyeditor';

        this.css = options.css || null;
        this.onLoaded = typeof options.onLoaded === 'function' ? options.onLoaded : null;
        this.randomString = Math.random().toString(36).substring(7);
        this.theme = options.theme || null;

        var doc = this.doc = element.ownerDocument;
        this.win = doc.defaultView || doc.parentWindow;

        this.attachEvents();
    }

    // initialize
    EasyEditor.prototype.attachEvents = function() {
        this.bootstrap();
        this.handleKeypress();
        this.handleResizeImage();
        this.utils();

        if(this.onLoaded !== null) {
            this.onLoaded.call(this);
        }
    };

    // destory editor
    EasyEditor.prototype.detachEvents = function() {
        var _this = this;

        $(_this.elem).removeClass(_this.className).removeAttr('contenteditable').unwrap();
    };

    // Adding necessary classes and attributes in editor
    EasyEditor.prototype.bootstrap = function() {
        var _this = this;
        $(_this.elem).attr('contentEditable', true).addClass(_this.className).wrap('<div class="'+ _this.className +'-wrapper"></div>');

        this.$wrapperElem = $(_this.elem).parent();

        if(_this.css !== null) {
            $(_this.elem).css(_this.css);
        }

        this.containerClass = '.' + _this.className +'-wrapper';

        if(typeof _this.elem === 'string') {
            _this.elem = $(_this.elem).get(0);
        }

        if(_this.theme !== null) {
            $(_this.elem).closest(_this.containerClass).addClass(_this.theme);
        }
    };


    // enter and paste key handler
    EasyEditor.prototype.handleKeypress = function(){
        var _this = this;

        $(_this.elem).keydown(function(e) {
            if(e.keyCode === 13 && _this.isSelectionInsideElement('li') === false) {
 //               e.preventDefault();
//
//                if(e.shiftKey === true) {
//                    document.execCommand('insertHTML', false, '<br>');
//                }
//                else {
//                    document.execCommand('insertHTML', false, '<br><br>');
//                }
//
//                return false;
            }
        });

        _this.elem.addEventListener('paste', function(e) {
            e.preventDefault();
            var text = e.clipboardData.getData('text/plain').replace(/\n/ig, '<br>');
            //document.execCommand('insertHTML', false, text);
            _this.doc.execCommand('insertHTML', false, text);
        });

    };

    EasyEditor.prototype.isSelectionInsideElement = function(tagName) {
        var sel, containerNode;
        var win = this.win,
            doc = this.doc;
        tagName = tagName.toUpperCase();
        if (win.getSelection) {
            sel = win.getSelection();
            if (sel.rangeCount > 0) {
                containerNode = sel.getRangeAt(0).commonAncestorContainer;
            }
        } else if ( (sel = doc.selection) && sel.type != "Control" ) {
            containerNode = sel.createRange().parentElement();
        }
        while (containerNode) {
            if (containerNode.nodeType == 1 && containerNode.tagName == tagName) {
                return true;
            }
            containerNode = containerNode.parentNode;
        }
        return false;
    };

    // allowing resizing image
    EasyEditor.prototype.handleResizeImage = function(){
        var _this = this;

        $('html').delegate(_this.containerClass + ' figure', 'click', function(event) {
            event.stopPropagation();
            $(this).addClass('is-resizable');
        });

        $('html').delegate(_this.containerClass + ' figure.is-resizable', 'mousemove', function(event) {
            $(this).find('img').css({ 'width' : $(this).width() + 'px' });
        });

        $(_this.doc).click(function() {
            $(_this.elem).find('figure').removeClass('is-resizable');
        });
    };

    // get selection
    EasyEditor.prototype.getSelection = function(){
        if (this.win.getSelection) {
            var selection = this.win.getSelection();

            if (selection.rangeCount) {
                return selection;
            }
        }

        return false;
    };

    // remove formatting
    EasyEditor.prototype.removeFormatting = function(arg){
        var _this = this;
        var inFullArea = arg.inFullArea;

        if(_this.isSelectionOutsideOfEditor() === true) {
            return false;
        }

        if(inFullArea === false) {
            var selection = _this.getSelection();
            var selectedText = selection.toString();

            if(selection && selectedText.length > 0) {

                var range = selection.getRangeAt(0);
                var $parent = $(range.commonAncestorContainer.parentNode);

                if($parent.attr('class') === _this.className || $parent.attr('class') === _this.className + '-wrapper') {
                    var node = _this.doc.createElement('span');
                    $(node).attr('data-value', 'temp').html(selectedText.replace(/\n/ig, '<br>'));
                    range.deleteContents();
                    range.insertNode(node);

                    $('[data-value="temp"]').contents().unwrap();
                }
                else {

                    var topMostParent;
                    var hasParentNode = false;
                    $.each($parent.parentsUntil(_this.elem), function(index, el) {
                        topMostParent = el;
                        hasParentNode = true;
                    });

                    if(hasParentNode === true) {
                        $(topMostParent).html($(topMostParent).text().replace(/\n/ig, '<br>')).contents().unwrap();
                    }
                    else {
                        $parent.contents().unwrap();
                    }

                }

            }
        }
        else {
            $(_this.elem).html($(_this.elem).text().replace(/\n/ig, '<br>'));
        }

        // _this.removeEmptyTags();
    };

    // removing empty tags
    EasyEditor.prototype.removeEmptyTags = function(){
        var _this = this;
        $(_this.elem).html( $(_this.elem).html().replace(/(<(?!\/)[^>]+>)+(<\/[^>]+>)+/, '') );
    };

    // remove block elemenet from selection
    EasyEditor.prototype.removeBlockElementFromSelection = function(selection, removeBr){
        var _this = this;
        var result;

        removeBr = removeBr === undefined ? false : removeBr;
        var removeBrNode = '';
        if(removeBr === true) {
            removeBrNode = ', br';
        }

        var range = selection.getRangeAt(0);
        var selectedHtml = range.cloneContents();
        var temp = _this.doc.createElement('temp');
        $(temp).html(selectedHtml);
        $(temp).find('h1, h2, h3, h4, h5, h6, p, div' + removeBrNode).each(function() { $(this).replaceWith(this.childNodes); });
        result = $(temp).html();

        return result;
    };

    // wrap selction with a tag
    EasyEditor.prototype.wrapSelectionWithNodeName = function(arg){
        var _this = this;
        if(_this.isSelectionOutsideOfEditor() === true) {
            return false;
        }

        var node = {
            name: 'span',
            blockElement: false,
            style: null,
            class: null,
            attribute: null,
            keepHtml: false
        };

        if(typeof arg === 'string') {
            node.name = arg;
        }
        else {
            node.name = arg.nodeName || node.name;
            node.blockElement = arg.blockElement || node.blockElement;
            node.style = arg.style || node.style;
            node.class = arg.class || node.class;
            node.attribute = arg.attribute || node.attribute;
            node.keepHtml = arg.keepHtml || node.keepHtml;
        }

        var selection = _this.getSelection();

        if(selection && selection.toString().length > 0 && selection.rangeCount) {
            // checking if already wrapped
            var isWrapped = _this.isAlreadyWrapped(selection, node);

            // wrap node
            var range = selection.getRangeAt(0).cloneRange();
            var tag = _this.doc.createElement(node.name);

                // adding necessary attribute to tag
                if(node.style !== null || node.class !== null || node.attribute !== null) {
                    tag = _this.addAttribute(tag, node);
                }

            // if selection contains html, surround contents has some problem with pre html tag and raw text selection
            if(_this.selectionContainsHtml(range)) {
                range = selection.getRangeAt(0);

                if(node.keepHtml === true) {
                    var clonedSelection = range.cloneContents();
                    var div = _this.doc.createElement('div');
                    div.appendChild(clonedSelection);
                    $(tag).html(div.innerHTML);
                }
                else {
                    tag.textContent = selection.toString();
                }

                range.deleteContents();
                range.insertNode(tag);

                if(range.commonAncestorContainer.localName === node.name) {
                    $(range.commonAncestorContainer).contents().unwrap();
                    _this.removeEmptyTags();
                }
            }
            else {
                range.surroundContents(tag);
                selection.removeAllRanges();
                selection.addRange(range);
            }

            if(isWrapped === true) {
                _this.removeWrappedDuplicateTag(tag);
            }

            _this.removeEmptyTags();
            //selection.removeAllRanges();
            selection.select();
            range.select();
        }
    };

    // wrap selection with unordered list
    EasyEditor.prototype.wrapSelectionWithList = function(tagname){
        var _this = this;
        tagname = tagname || 'ul';

        // preventing outside selection
        if(_this.isSelectionOutsideOfEditor() === true) {
            return false;
        }

        // if text selected
        var selection = _this.getSelection();
        if(selection && selection.toString().length > 0 && selection.rangeCount) {
            var selectedHtml = _this.removeBlockElementFromSelection(selection, true);
            var listArray = selectedHtml.split('\n').filter(function(v){return v!=='';});
            var wrappedListHtml = $.map(listArray, function(item) {
                return '<li>' + $.trim(item) + '</li>';
            });

            var node = _this.doc.createElement(tagname);
            $(node).html(wrappedListHtml);

            var range = selection.getRangeAt(0);
            range.deleteContents();
            range.insertNode(node);

            selection.removeAllRanges();
        }

    };

    // if selection contains html tag, surround content fails if selection contains html
    EasyEditor.prototype.selectionContainsHtml = function(range){
        var _this = this;
        if(range.startContainer.parentNode.className === _this.className + '-wrapper') return false;
        else return true;
    };

    // if already wrapped with same tag
    EasyEditor.prototype.isAlreadyWrapped = function(selection, node){
        var _this = this;
        var range = selection.getRangeAt(0);
        var el = $(range.commonAncestorContainer);
        var result = false;

        if( el.parent().prop('tagName').toLowerCase() === node.name && el.parent().hasClass(_this.className) === false ) {
            result = true;
        }
        else if(node.blockElement === true) {
            $.each(el.parentsUntil(_this.elem), function(index, el) {
                var tag = el.tagName.toLowerCase();
                if( $.inArray(tag, ['h1', 'h2', 'h3', 'h4', 'h5', 'h6']) !== -1 ) {
                    result = true;
                }
            });
        }
        else {
            $.each(el.parentsUntil(_this.elem), function(index, el) {
                var tag = el.tagName.toLowerCase();
                if( tag === node.name ) {
                    result = true;
                }
            });
        }

        return result;
    };

    // remove wrap if already wrapped with same tag
    EasyEditor.prototype.removeWrappedDuplicateTag = function(tag){
        var _this = this;
        var tagName = tag.tagName;

        $(tag).unwrap();

        if($(tag).prop('tagName') === tagName && $(tag).parent().hasClass(_this.className) === false && $(tag).parent().hasClass(_this.className + '-wrapper')) {
            $(tag).unwrap();
        }
    };

    // adding attribute in tag
    EasyEditor.prototype.addAttribute = function(tag, node){
        if(node.style !== null) {
            $(tag).attr('style', node.style);
        }

        if(node.class !== null) {
            $(tag).addClass(node.class);
        }

        if(node.attribute !== null) {
            if($.isArray(node.attribute) === true) {
                $(tag).attr(node.attribute[0], node.attribute[1]);
            }
            else {
                $(tag).attr(node.attribute);
            }
        }

        return tag;
    };

    // insert a node into cursor point in editor
    EasyEditor.prototype.insertAtCaret = function(node){
        var _this = this;
        if(_this.isSelectionOutsideOfEditor() === true) {
            return false;
        }

        if(_this.getSelection()) {
            var range = _this.getSelection().getRangeAt(0);
            range.insertNode(node);
        }
        else {
            $(node).appendTo(_this.elem);
        }
    };

    // checking if selection outside of editor or not
    EasyEditor.prototype.isSelectionOutsideOfEditor = function(){
        //return false;
        return !this.elementContainsSelection(this.elem);
    };

    EasyEditor.prototype.isActive = function(){
        //return false;
        return this.elementContainsSelection(this.elem);
    };
    EasyEditor.prototype.readonly = function(readonly) {
        if (readonly === undefined) {
            return $(this.elem).attr('contentEditable');
        } else {
            $(this.elem).attr('contentEditable', readonly && true);
            return this;
        }
    };
    // node contains in containers or not
    EasyEditor.prototype.isOrContains = function(node, container) {
        while (node) {
            if (node === container) {
                return true;
            }
            node = node.parentNode;
        }
        return false;
    };

    // selected text is inside container
    EasyEditor.prototype.elementContainsSelection = function(el) {
        var _this = this;
        var sel;
        if (this.win.getSelection) {
            sel = this.win.getSelection();
            if (sel.rangeCount > 0) {
                for (var i = 0; i < sel.rangeCount; ++i) {
                    if (!_this.isOrContains(sel.getRangeAt(i).commonAncestorContainer, el)) {
                        return false;
                    }
                }
                return true;
            }
        } else if ( (sel = _this.doc.selection) && sel.type !== "Control") {
            return _this.isOrContains(sel.createRange().parentElement(), el);
        }
        return false;
    };

    // insert html chunk into editor's temp tag
    EasyEditor.prototype.insertHtml = function(html){
        var _this = this;
        $(_this.elem).find('temp').html(html);
    };

    // utility of editor
    EasyEditor.prototype.utils = function(){
        var _this = this;

        $('html').delegate('.'+ _this.className +'-modal-close', 'click', function(event) {
            event.preventDefault();
            _this.closeModal('#' + $(this).closest('.'+ _this.className + '-modal').attr('id'));
        });

        if( $('.' + _this.randomString + '-bind').length > 0 ) {
            var bindData;
            $('html').delegate(_this.elem, 'click keyup', function() {
                var el = _this.elem;
                clearTimeout(bindData);
                bindData = setTimeout(function(){ $('.' + _this.randomString + '-bind').html( $(el).html() ); }, 250);
            });
        }

        $(_this.doc).click(function(event) {
            $('.' + _this.className).closest('.' + _this.className + '-wrapper').find('.' + _this.className + '-toolbar > ul > li > ul').hide();
        });
    };

    // youtube video id from url
    EasyEditor.prototype.getYoutubeVideoIdFromUrl = function(url){
        if(url.length === 0) return false;
        var videoId = '';
        url = url.replace(/(>|<)/gi,'').split(/(vi\/|v=|\/v\/|youtu\.be\/|\/embed\/)/);
        if(url[2] !== undefined) {
            videoId = url[2].split(/[^0-9a-z_\-]/i);
            videoId = videoId[0];
        }
        else {
            videoId = url;
        }
        return videoId;
    };

    // opening modal window
    EasyEditor.prototype.openModal = function(selector){
        var temp = this.doc.createElement('temp');
        temp.textContent = '.';
        this.insertAtCaret(temp);

        $(selector).removeClass('is-hidden');
    };

    // closing modal window
    EasyEditor.prototype.closeModal = function(selector){
        var _this = this;

        $(selector).addClass('is-hidden').find('input').val('');
        $(selector).find('.' + _this.className + '-modal-content-body-loader').css('width', '0');
        var $temp = $(this.elem).find('temp');

        if($temp.html() === '.') {
            $temp.remove();
        }
        else {
            $temp.contents().unwrap();
        }

        $(this.elem).focus();
    };

    EasyEditor.prototype.bold = function(){
       this.doc.execCommand("Bold");
       //this.wrapSelectionWithNodeName({ nodeName: 'strong', keepHtml: true });
    };

    EasyEditor.prototype.italic = function(){
       this.doc.execCommand("Italic");
 	   //this.wrapSelectionWithNodeName({ nodeName: 'em', keepHtml: true });
    };

    EasyEditor.prototype.strike = function(){
       this.doc.execCommand("strikethrough");
       //this.wrapSelectionWithNodeName({ nodeName: 'strong', keepHtml: true });
    };

    EasyEditor.prototype.strike = function(){
       this.doc.execCommand("strikethrough");
       //this.wrapSelectionWithNodeName({ nodeName: 'strong', keepHtml: true });
    };

    EasyEditor.prototype.listOl = function(){
       this.doc.execCommand("insertorderedlist");
       //this.wrapSelectionWithNodeName({ nodeName: 'strong', keepHtml: true });
    };

    EasyEditor.prototype.listUl = function(){
       this.doc.execCommand("insertunorderedlist");
       //this.wrapSelectionWithNodeName({ nodeName: 'strong', keepHtml: true });
    };

    EasyEditor.prototype.h2 = function(){
       this.doc.execCommand("h2");
        //this.wrapSelectionWithNodeName({ nodeName: 'h2', blockElement: true });
    };

    EasyEditor.prototype.h3 = function(){
       this.doc.execCommand("h3");
        //this.wrapSelectionWithNodeName({ nodeName: 'h3', blockElement: true });
    };

    EasyEditor.prototype.h4 = function(){
       this.doc.execCommand("h4");
        //this.wrapSelectionWithNodeName({ nodeName: 'h4', blockElement: true });
    };

    EasyEditor.prototype.x = function(){
       this.doc.execCommand("h4");
    };

    EasyEditor.prototype.alignleft = function(){
       this.doc.execCommand("justifyleft");
        //this.wrapSelectionWithNodeName({ nodeName: 'p', style: 'text-align: left', class: 'text-left', keepHtml: true });
    };

    EasyEditor.prototype.aligncenter = function(){
       this.doc.execCommand("justifycenter");
        //this.wrapSelectionWithNodeName({ nodeName: 'p', style: 'text-align: center', class: 'text-center', keepHtml: true });
    };

    EasyEditor.prototype.alignright = function(){
       this.doc.execCommand("justifyright");
        //this.wrapSelectionWithNodeName({ nodeName: 'p', style: 'text-align: right', class: 'text-right', keepHtml: true });
    };

    EasyEditor.prototype.alignfull = function(){
       this.doc.execCommand("justifyfull");
        //this.wrapSelectionWithNodeName({ nodeName: 'p', style: 'text-align: right', class: 'text-right', keepHtml: true });
    };

    EasyEditor.prototype.blockquote = function(){
       this.doc.execCommand("blockquote");
        //this.wrapSelectionWithNodeName({ nodeName: 'blockquote' });
    };

    EasyEditor.prototype.code = function(){
       this.doc.execCommand("p");
        //this.wrapSelectionWithNodeName({ nodeName: 'pre' });
    };

    EasyEditor.prototype.inserthorizontalrule = function(){
       this.doc.execCommand("inserthorizontalrule");
    };


    EasyEditor.prototype.image = function(){
       var url = prompt('Insert Image URL','http://');
       var urlRegex = new RegExp('^((http|https)://|(mailto:)|(//))[a-z0-9]', 'i');
       if (url !== null && url !== '' && url !== 'http://' && urlRegex.test(url)) {
    	   this.doc.execCommand("insertimage", false, url);
       }
    };

    EasyEditor.prototype.link = function(){
        var url = prompt('Insert URL','http://');
        var urlRegex = new RegExp('^((http|https)://|(mailto:)|(//))[a-z0-9]', 'i');
        if (url !== null && url !== '' && url !== 'http://' && urlRegex.test(url)) {
     	   this.doc.execCommand("createlink", false, url);
        }
        //this.wrapSelectionWithNodeName({ nodeName: 'a', attribute: ['href', prompt('Insert link', '')] });
    };

    EasyEditor.prototype.indent = function(){
       this.doc.execCommand("indent");
    };

    EasyEditor.prototype.outdent = function(){
       this.doc.execCommand("outdent");
    };


      EasyEditor.prototype.toMd = function() {
    	return  toMarkdown(this.elem.innerHTML);
      };

    window.EasyEditor = EasyEditor;

    $.fn.easyEditor = function ( options ) {
        return this.each(function () {
            if (!$.data(this, 'plugin_easyEditor')) {
                $.data(this, 'plugin_easyEditor',
                new EasyEditor( this, options ));
            }
        });
    };

})(jQuery, document, window);

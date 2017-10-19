﻿/**
 * @license Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */
        CKEDITOR.dialog.add('tdcheckbox', function (editor) {
            return {
                title: editor.lang.tdforms.tdcheckbox.dialogTitle,
                minWidth: 350,
                minHeight: 140,
                onShow: function () {
                    delete this.checkbox;

                    var element = this.getParentEditor().getSelection().getSelectedElement();

                    if (element && element.getAttribute('type') === 'checkbox') {
                        this.checkbox = element;
                        this.setupContent(element);
                    }
                },
                onOk: function () {
                    var editor, widget_json = '',
                            element = this.checkbox,
                            fieldId = '',
                            field_type = 'checkbox',
                            field_name = this.getValueOf('info', 'txtName'),
                            field_value = this.getValueOf('info', 'txtValue'),
                            cmbSelected = this.getValueOf('info', 'cmbSelected'),
                            isInsertMode = !element;
                    widget_json += '{' + '"field_name":' + '"' + field_name + '",' + '"field_type":' + '"' + field_type + '",'
                            + '"field_attr":' + '{' + '"title":' + '"' + field_name + '",' + '"class":' + '"' + field_type + '",'
                            + '"value":' + '"' + field_value + '",' + '"checked":' + '"' + cmbSelected + '"' + '}}';
                    if (isInsertMode) {
                        fieldId = $.ajax({type: "post", url: g_requestURL, data: {fieldInfo: widget_json, formID: g_form_id}, cache: false, async: false}).responseText;
                        if (fieldId === 'repeat') {
                            _originalAlert(field_name + ' 字段已存在！');
                            return false;
                        } else {
                            editor = this.getParentEditor();
                            element = editor.document.createElement('input');
                            element.setAttribute('type', 'checkbox');
                            element.setAttribute('name', 'data_' + fieldId);
                            element.setAttribute('id', 'data_' + fieldId);
                            editor.insertElement(element);
                        }
                    } else {
                        fieldId = element.getAttribute('name').split('_')[1];
                        fieldId = $.ajax({type: "post", url: g_requestURL, data: {fieldInfo: widget_json, formID: g_form_id, fieldId: fieldId}, cache: false, async: false}).responseText;
                        if (fieldId === 'repeat') {
                            _originalAlert(field_name + ' 字段已存在！');
                            return false;
                        }
                    }
                    this.commitContent({element: element});
                },
                contents: [
                    {
                        id: 'info',
                        startupFocus: 'txtName',
                        elements: [
                            {
                                id: 'txtName',
                                type: 'text',
                                label: editor.lang.common.controlName,
                                validate: CKEDITOR.dialog.validate.notEmpty(editor.lang.common.validateControlFailed),
                                'default': '',
                                accessKey: 'N',
                                setup: function (element) {
                                    this.setValue(element.getAttribute('title') || '');
                                },
                                commit: function (data) {
                                    var element = data.element;
                                    if (this.getValue())
                                        element.setAttribute('title', this.getValue());
                                }
                            },
                            {
                                id: 'txtValue',
                                type: 'text',
                                label: editor.lang.common.value,
                                'default': '',
                                accessKey: 'V',
                                setup: function (element) {
                                    var value = element.getAttribute('value');
                                    // IE Return 'on' as default attr value.
                                    this.setValue(CKEDITOR.env.ie && value == 'on' ? '' : value);
                                },
                                commit: function (data) {
                                    var element = data.element,
                                            value = this.getValue();

                                    if (value && !(CKEDITOR.env.ie && value == 'on'))
                                        element.setAttribute('value', value);
                                    else {
                                        if (CKEDITOR.env.ie) {
                                            // Remove attribute 'value' of checkbox (#4721).
                                            var checkbox = new CKEDITOR.dom.element('input', element.getDocument());
                                            element.copyAttributes(checkbox, {value: 1});
                                            checkbox.replace(element);
                                            editor.getSelection().selectElement(checkbox);
                                            data.element = checkbox;
                                        } else
                                            element.removeAttribute('value');
                                    }
                                }
                            },
                            {
                                id: 'cmbSelected',
                                type: 'checkbox',
                                label: editor.lang.tdforms.tdcheckbox.selected,
                                'default': '',
                                accessKey: 'S',
                                value: "checked",
                                setup: function (element) {
                                    this.setValue(element.getAttribute('checked'));
                                },
                                commit: function (data) {
                                    var element = data.element;

                                    if (CKEDITOR.env.ie) {
                                        var isElementChecked = !!element.getAttribute('checked'),
                                                isChecked = !!this.getValue();

                                        if (isElementChecked != isChecked) {
                                            var replace = CKEDITOR.dom.element.createFromHtml('<input type="checkbox"' + (isChecked ? ' checked="checked"' : '')
                                                    + '/>', editor.document);

                                            element.copyAttributes(replace, {type: 1, checked: 1});
                                            replace.replace(element);
                                            editor.getSelection().selectElement(replace);
                                            data.element = replace;
                                        }
                                    } else {
                                        var value = this.getValue();
                                        if (value)
                                            element.setAttribute('checked', 'checked');
                                        else
                                            element.removeAttribute('checked');
                                    }
                                }
                            }
                        ]
                    }
                ]
            };
        });

function _toConsumableArray(e){return _arrayWithoutHoles(e)||_iterableToArray(e)||_unsupportedIterableToArray(e)||_nonIterableSpread()}function _nonIterableSpread(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}function _unsupportedIterableToArray(e,t){if(e){if("string"==typeof e)return _arrayLikeToArray(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?_arrayLikeToArray(e,t):void 0}}function _iterableToArray(e){if("undefined"!=typeof Symbol&&Symbol.iterator in Object(e))return Array.from(e)}function _arrayWithoutHoles(e){if(Array.isArray(e))return _arrayLikeToArray(e)}function _arrayLikeToArray(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,i=new Array(t);n<t;n++)i[n]=e[n];return i}function _defineProperties(e,t){for(var n=0;n<t.length;n++){var i=t[n];i.enumerable=i.enumerable||!1,i.configurable=!0,"value"in i&&(i.writable=!0),Object.defineProperty(e,i.key,i)}}function _createClass(e,t,n){return t&&_defineProperties(e.prototype,t),n&&_defineProperties(e,n),e}function _inherits(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,writable:!0,configurable:!0}}),t&&_setPrototypeOf(e,t)}function _setPrototypeOf(e,t){return(_setPrototypeOf=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function _createSuper(e){var t=_isNativeReflectConstruct();return function(){var n,i=_getPrototypeOf(e);if(t){var l=_getPrototypeOf(this).constructor;n=Reflect.construct(i,arguments,l)}else n=i.apply(this,arguments);return _possibleConstructorReturn(this,n)}}function _possibleConstructorReturn(e,t){return!t||"object"!=typeof t&&"function"!=typeof t?_assertThisInitialized(e):t}function _assertThisInitialized(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}function _isNativeReflectConstruct(){if("undefined"==typeof Reflect||!Reflect.construct)return!1;if(Reflect.construct.sham)return!1;if("function"==typeof Proxy)return!0;try{return Date.prototype.toString.call(Reflect.construct(Date,[],(function(){}))),!0}catch(e){return!1}}function _getPrototypeOf(e){return(_getPrototypeOf=Object.setPrototypeOf?Object.getPrototypeOf:function(e){return e.__proto__||Object.getPrototypeOf(e)})(e)}function _classCallCheck(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(window.webpackJsonp=window.webpackJsonp||[]).push([[1],{Y1Mv:function(e,t,n){"use strict";n.d(t,"a",(function(){return r})),n.d(t,"b",(function(){return f}));var i=n("8Y7J"),l=(n("ZTz/"),n("SVse")),o=n("1O3W"),a=n("9gLZ"),r=(n("9b/N"),n("1z/I"),n("SCoL"),n("7KAL"),n("UhP/"),n("YEUz"),n("Q2Ze"),n("s7LF"),i.xb({encapsulation:2,styles:[".mat-select{display:inline-block;width:100%;outline:none}.mat-select-trigger{display:inline-table;cursor:pointer;position:relative;box-sizing:border-box}.mat-select-disabled .mat-select-trigger{-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none;cursor:default}.mat-select-value{display:table-cell;max-width:0;width:100%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap}.mat-select-value-text{white-space:nowrap;overflow:hidden;text-overflow:ellipsis}.mat-select-arrow-wrapper{display:table-cell;vertical-align:middle}.mat-form-field-appearance-fill .mat-select-arrow-wrapper{transform:translateY(-50%)}.mat-form-field-appearance-outline .mat-select-arrow-wrapper{transform:translateY(-25%)}.mat-form-field-appearance-standard.mat-form-field-has-label .mat-select:not(.mat-select-empty) .mat-select-arrow-wrapper{transform:translateY(-50%)}.mat-form-field-appearance-standard .mat-select.mat-select-empty .mat-select-arrow-wrapper{transition:transform 400ms cubic-bezier(0.25, 0.8, 0.25, 1)}._mat-animation-noopable.mat-form-field-appearance-standard .mat-select.mat-select-empty .mat-select-arrow-wrapper{transition:none}.mat-select-arrow{width:0;height:0;border-left:5px solid transparent;border-right:5px solid transparent;border-top:5px solid;margin:0 4px}.mat-select-panel-wrap{flex-basis:100%}.mat-select-panel{min-width:112px;max-width:280px;overflow:auto;-webkit-overflow-scrolling:touch;padding-top:0;padding-bottom:0;max-height:256px;min-width:100%;border-radius:4px}.cdk-high-contrast-active .mat-select-panel{outline:solid 1px}.mat-select-panel .mat-optgroup-label,.mat-select-panel .mat-option{font-size:inherit;line-height:3em;height:3em}.mat-form-field-type-mat-select:not(.mat-form-field-disabled) .mat-form-field-flex{cursor:pointer}.mat-form-field-type-mat-select .mat-form-field-label{width:calc(100% - 18px)}.mat-select-placeholder{transition:color 400ms 133.3333333333ms cubic-bezier(0.25, 0.8, 0.25, 1)}._mat-animation-noopable .mat-select-placeholder{transition:none}.mat-form-field-hide-placeholder .mat-select-placeholder{color:transparent;-webkit-text-fill-color:transparent;transition:none;display:block}\n"],data:{animation:[{type:7,name:"transformPanelWrap",definitions:[{type:1,expr:"* => void",animation:{type:11,selector:"@transformPanel",animation:[{type:9,options:null}],options:{optional:!0}},options:null}],options:{}},{type:7,name:"transformPanel",definitions:[{type:0,name:"void",styles:{type:6,styles:{transform:"scaleY(0.8)",minWidth:"100%",opacity:0},offset:null},options:void 0},{type:0,name:"showing",styles:{type:6,styles:{opacity:1,minWidth:"calc(100% + 32px)",transform:"scaleY(1)"},offset:null},options:void 0},{type:0,name:"showing-multiple",styles:{type:6,styles:{opacity:1,minWidth:"calc(100% + 64px)",transform:"scaleY(1)"},offset:null},options:void 0},{type:1,expr:"void => *",animation:{type:4,styles:null,timings:"120ms cubic-bezier(0, 0, 0.2, 1)"},options:null},{type:1,expr:"* => void",animation:{type:4,styles:{type:6,styles:{opacity:0},offset:null},timings:"100ms 25ms linear"},options:null}],options:{}}]}}));function s(e){return i.bc(0,[(e()(),i.zb(0,0,null,null,1,"span",[["class","mat-select-placeholder"]],null,null,null,null,null)),(e()(),i.Yb(1,null,["",""]))],null,(function(e,t){e(t,1,0,t.component.placeholder||"\xa0")}))}function c(e){return i.bc(0,[(e()(),i.zb(0,0,null,null,1,"span",[],null,null,null,null,null)),(e()(),i.Yb(1,null,["",""]))],null,(function(e,t){e(t,1,0,t.component.triggerValue||"\xa0")}))}function u(e){return i.bc(0,[i.Nb(null,0),(e()(),i.ib(0,null,null,0))],null,null)}function h(e){return i.bc(0,[(e()(),i.zb(0,0,null,null,5,"span",[["class","mat-select-value-text"]],null,null,null,null,null)),i.yb(1,16384,null,0,l.q,[],{ngSwitch:[0,"ngSwitch"]},null),(e()(),i.ib(16777216,null,null,1,null,c)),i.yb(3,16384,null,0,l.s,[i.Q,i.N,l.q],null,null),(e()(),i.ib(16777216,null,null,1,null,u)),i.yb(5,278528,null,0,l.r,[i.Q,i.N,l.q],{ngSwitchCase:[0,"ngSwitchCase"]},null)],(function(e,t){e(t,1,0,!!t.component.customTrigger),e(t,5,0,!0)}),null)}function p(e){return i.bc(0,[(e()(),i.zb(0,0,null,null,3,"div",[["class","mat-select-panel-wrap"]],[[24,"@transformPanelWrap",0]],null,null,null,null)),(e()(),i.zb(1,0,[[2,0],["panel",1]],null,2,"div",[],[[1,"id",0],[24,"@transformPanel",0],[4,"transformOrigin",null],[4,"font-size","px"]],[[null,"@transformPanel.done"],[null,"keydown"]],(function(e,t,n){var i=!0,l=e.component;return"@transformPanel.done"===t&&(i=!1!==l._panelDoneAnimatingStream.next(n.toState)&&i),"keydown"===t&&(i=!1!==l._handleKeydown(n)&&i),i}),null,null)),i.yb(2,278528,null,0,l.k,[i.t,i.u,i.l,i.F],{klass:[0,"klass"],ngClass:[1,"ngClass"]},null),i.Nb(null,1)],(function(e,t){var n=t.component;e(t,2,0,i.Gb(1,"mat-select-panel ",n._getPanelTheme(),""),n.panelClass)}),(function(e,t){var n=t.component;e(t,0,0,void 0),e(t,1,0,n.id+"-panel",n.multiple?"showing-multiple":"showing",n._transformOrigin,n._triggerFontSize)}))}function f(e){return i.bc(2,[i.Ub(671088640,1,{trigger:0}),i.Ub(671088640,2,{panel:0}),i.Ub(671088640,3,{overlayDir:0}),(e()(),i.zb(3,0,[[1,0],["trigger",1]],null,9,"div",[["aria-hidden","true"],["cdk-overlay-origin",""],["class","mat-select-trigger"]],null,[[null,"click"]],(function(e,t,n){var i=!0;return"click"===t&&(i=!1!==e.component.toggle()&&i),i}),null,null)),i.yb(4,16384,[["origin",4]],0,o.b,[i.l],null,null),(e()(),i.zb(5,0,null,null,5,"div",[["class","mat-select-value"]],null,null,null,null,null)),i.yb(6,16384,null,0,l.q,[],{ngSwitch:[0,"ngSwitch"]},null),(e()(),i.ib(16777216,null,null,1,null,s)),i.yb(8,278528,null,0,l.r,[i.Q,i.N,l.q],{ngSwitchCase:[0,"ngSwitchCase"]},null),(e()(),i.ib(16777216,null,null,1,null,h)),i.yb(10,278528,null,0,l.r,[i.Q,i.N,l.q],{ngSwitchCase:[0,"ngSwitchCase"]},null),(e()(),i.zb(11,0,null,null,1,"div",[["class","mat-select-arrow-wrapper"]],null,null,null,null,null)),(e()(),i.zb(12,0,null,null,0,"div",[["class","mat-select-arrow"]],null,null,null,null,null)),(e()(),i.ib(16777216,null,null,1,(function(e,t,n){var i=!0,l=e.component;return"backdropClick"===t&&(i=!1!==l.close()&&i),"attach"===t&&(i=!1!==l._onAttached()&&i),"detach"===t&&(i=!1!==l.close()&&i),i}),p)),i.yb(14,671744,[[3,4]],0,o.a,[o.c,i.N,i.Q,o.j,[2,a.b]],{origin:[0,"origin"],positions:[1,"positions"],offsetY:[2,"offsetY"],minWidth:[3,"minWidth"],backdropClass:[4,"backdropClass"],scrollStrategy:[5,"scrollStrategy"],open:[6,"open"],hasBackdrop:[7,"hasBackdrop"],lockPosition:[8,"lockPosition"]},{backdropClick:"backdropClick",attach:"attach",detach:"detach"})],(function(e,t){var n=t.component;e(t,6,0,n.empty),e(t,8,0,!0),e(t,10,0,!1),e(t,14,0,i.Ob(t,4),n._positions,n._offsetY,null==n._triggerRect?null:n._triggerRect.width,"cdk-overlay-transparent-backdrop",n._scrollStrategy,n.panelOpen,"","")}),null)}},"ZTz/":function(e,t,n){"use strict";n.d(t,"a",(function(){return k})),n.d(t,"b",(function(){return b})),n.d(t,"c",(function(){return O})),n.d(t,"d",(function(){return w})),n.d(t,"e",(function(){return I})),n("1O3W");var i=n("8Y7J"),l=n("UhP/"),o=n("YEUz"),a=n("8LU1"),r=n("CtHx"),s=n("Ht+U"),c=n("XNiG"),u=n("NXyV"),h=n("VRyK"),p=n("JX91"),f=n("eIep"),d=n("IzEk"),_=n("pLZG"),g=n("lJxs"),y=n("/uUt"),m=n("1G5W");n("GS7A");var v=0,b=new i.r("mat-select-scroll-strategy");function O(e){return function(){return e.scrollStrategies.reposition()}}var k=new i.r("MAT_SELECT_CONFIG"),C=function e(t,n){_classCallCheck(this,e),this.source=t,this.value=n},w=function(e){_inherits(n,e);var t=_createSuper(n);function n(e,l,o,a,r,s,y,m,b,O,k,C,w,I){var S;return _classCallCheck(this,n),(S=t.call(this,r,a,y,m,O))._viewportRuler=e,S._changeDetectorRef=l,S._ngZone=o,S._dir=s,S._parentFormField=b,S.ngControl=O,S._liveAnnouncer=w,S._panelOpen=!1,S._required=!1,S._scrollTop=0,S._multiple=!1,S._compareWith=function(e,t){return e===t},S._uid="mat-select-".concat(v++),S._destroy=new c.a,S._triggerFontSize=0,S._onChange=function(){},S._onTouched=function(){},S._optionIds="",S._transformOrigin="top",S._panelDoneAnimatingStream=new c.a,S._offsetY=0,S._positions=[{originX:"start",originY:"top",overlayX:"start",overlayY:"top"},{originX:"start",originY:"bottom",overlayX:"start",overlayY:"bottom"}],S._disableOptionCentering=!1,S._focused=!1,S.controlType="mat-select",S.ariaLabel="",S.optionSelectionChanges=Object(u.a)((function(){var e=S.options;return e?e.changes.pipe(Object(p.a)(e),Object(f.a)((function(){return Object(h.a).apply(void 0,_toConsumableArray(e.map((function(e){return e.onSelectionChange}))))}))):S._ngZone.onStable.asObservable().pipe(Object(d.a)(1),Object(f.a)((function(){return S.optionSelectionChanges})))})),S.openedChange=new i.o,S._openedStream=S.openedChange.pipe(Object(_.a)((function(e){return e})),Object(g.a)((function(){}))),S._closedStream=S.openedChange.pipe(Object(_.a)((function(e){return!e})),Object(g.a)((function(){}))),S.selectionChange=new i.o,S.valueChange=new i.o,S.ngControl&&(S.ngControl.valueAccessor=_assertThisInitialized(S)),S._scrollStrategyFactory=C,S._scrollStrategy=S._scrollStrategyFactory(),S.tabIndex=parseInt(k)||0,S.id=S.id,I&&(null!=I.disableOptionCentering&&(S.disableOptionCentering=I.disableOptionCentering),null!=I.typeaheadDebounceInterval&&(S.typeaheadDebounceInterval=I.typeaheadDebounceInterval)),S}return _createClass(n,[{key:"ngOnInit",value:function(){var e=this;this._selectionModel=new r.c(this.multiple),this.stateChanges.next(),this._panelDoneAnimatingStream.pipe(Object(y.a)(),Object(m.a)(this._destroy)).subscribe((function(){e.panelOpen?(e._scrollTop=0,e.openedChange.emit(!0)):(e.openedChange.emit(!1),e.overlayDir.offsetX=0,e._changeDetectorRef.markForCheck())})),this._viewportRuler.change().pipe(Object(m.a)(this._destroy)).subscribe((function(){e._panelOpen&&(e._triggerRect=e.trigger.nativeElement.getBoundingClientRect(),e._changeDetectorRef.markForCheck())}))}},{key:"ngAfterContentInit",value:function(){var e=this;this._initKeyManager(),this._selectionModel.changed.pipe(Object(m.a)(this._destroy)).subscribe((function(e){e.added.forEach((function(e){return e.select()})),e.removed.forEach((function(e){return e.deselect()}))})),this.options.changes.pipe(Object(p.a)(null),Object(m.a)(this._destroy)).subscribe((function(){e._resetOptions(),e._initializeSelection()}))}},{key:"ngDoCheck",value:function(){this.ngControl&&this.updateErrorState()}},{key:"ngOnChanges",value:function(e){e.disabled&&this.stateChanges.next(),e.typeaheadDebounceInterval&&this._keyManager&&this._keyManager.withTypeAhead(this._typeaheadDebounceInterval)}},{key:"ngOnDestroy",value:function(){this._destroy.next(),this._destroy.complete(),this.stateChanges.complete()}},{key:"toggle",value:function(){this.panelOpen?this.close():this.open()}},{key:"open",value:function(){var e=this;!this.disabled&&this.options&&this.options.length&&!this._panelOpen&&(this._triggerRect=this.trigger.nativeElement.getBoundingClientRect(),this._triggerFontSize=parseInt(getComputedStyle(this.trigger.nativeElement).fontSize||"0"),this._panelOpen=!0,this._keyManager.withHorizontalOrientation(null),this._calculateOverlayPosition(),this._highlightCorrectOption(),this._changeDetectorRef.markForCheck(),this._ngZone.onStable.asObservable().pipe(Object(d.a)(1)).subscribe((function(){e._triggerFontSize&&e.overlayDir.overlayRef&&e.overlayDir.overlayRef.overlayElement&&(e.overlayDir.overlayRef.overlayElement.style.fontSize="".concat(e._triggerFontSize,"px"))})))}},{key:"close",value:function(){this._panelOpen&&(this._panelOpen=!1,this._keyManager.withHorizontalOrientation(this._isRtl()?"rtl":"ltr"),this._changeDetectorRef.markForCheck(),this._onTouched())}},{key:"writeValue",value:function(e){this.options&&this._setSelectionByValue(e)}},{key:"registerOnChange",value:function(e){this._onChange=e}},{key:"registerOnTouched",value:function(e){this._onTouched=e}},{key:"setDisabledState",value:function(e){this.disabled=e,this._changeDetectorRef.markForCheck(),this.stateChanges.next()}},{key:"_isRtl",value:function(){return!!this._dir&&"rtl"===this._dir.value}},{key:"_handleKeydown",value:function(e){this.disabled||(this.panelOpen?this._handleOpenKeydown(e):this._handleClosedKeydown(e))}},{key:"_handleClosedKeydown",value:function(e){var t=e.keyCode,n=t===s.d||t===s.p||t===s.i||t===s.m,i=t===s.f||t===s.n,l=this._keyManager;if(!l.isTyping()&&i&&!Object(s.s)(e)||(this.multiple||e.altKey)&&n)e.preventDefault(),this.open();else if(!this.multiple){var o=this.selected;t===s.h||t===s.e?(t===s.h?l.setFirstItemActive():l.setLastItemActive(),e.preventDefault()):l.onKeydown(e);var a=this.selected;a&&o!==a&&this._liveAnnouncer.announce(a.viewValue,1e4)}}},{key:"_handleOpenKeydown",value:function(e){var t=this._keyManager,n=e.keyCode,i=n===s.d||n===s.p,l=t.isTyping();if(n===s.h||n===s.e)e.preventDefault(),n===s.h?t.setFirstItemActive():t.setLastItemActive();else if(i&&e.altKey)e.preventDefault(),this.close();else if(l||n!==s.f&&n!==s.n||!t.activeItem||Object(s.s)(e))if(!l&&this._multiple&&n===s.a&&e.ctrlKey){e.preventDefault();var o=this.options.some((function(e){return!e.disabled&&!e.selected}));this.options.forEach((function(e){e.disabled||(o?e.select():e.deselect())}))}else{var a=t.activeItemIndex;t.onKeydown(e),this._multiple&&i&&e.shiftKey&&t.activeItem&&t.activeItemIndex!==a&&t.activeItem._selectViaInteraction()}else e.preventDefault(),t.activeItem._selectViaInteraction()}},{key:"_onFocus",value:function(){this.disabled||(this._focused=!0,this.stateChanges.next())}},{key:"_onBlur",value:function(){this._focused=!1,this.disabled||this.panelOpen||(this._onTouched(),this._changeDetectorRef.markForCheck(),this.stateChanges.next())}},{key:"_onAttached",value:function(){var e=this;this.overlayDir.positionChange.pipe(Object(d.a)(1)).subscribe((function(){e._changeDetectorRef.detectChanges(),e._calculateOverlayOffsetX(),e.panel.nativeElement.scrollTop=e._scrollTop}))}},{key:"_getPanelTheme",value:function(){return this._parentFormField?"mat-".concat(this._parentFormField.color):""}},{key:"_initializeSelection",value:function(){var e=this;Promise.resolve().then((function(){e._setSelectionByValue(e.ngControl?e.ngControl.value:e._value),e.stateChanges.next()}))}},{key:"_setSelectionByValue",value:function(e){var t=this;if(this.multiple&&e){if(!Array.isArray(e))throw Error("Value must be an array in multiple-selection mode.");this._selectionModel.clear(),e.forEach((function(e){return t._selectValue(e)})),this._sortValues()}else{this._selectionModel.clear();var n=this._selectValue(e);n?this._keyManager.setActiveItem(n):this.panelOpen||this._keyManager.setActiveItem(-1)}this._changeDetectorRef.markForCheck()}},{key:"_selectValue",value:function(e){var t=this,n=this.options.find((function(n){try{return null!=n.value&&t._compareWith(n.value,e)}catch(l){return Object(i.X)()&&console.warn(l),!1}}));return n&&this._selectionModel.select(n),n}},{key:"_initKeyManager",value:function(){var e=this;this._keyManager=new o.b(this.options).withTypeAhead(this._typeaheadDebounceInterval).withVerticalOrientation().withHorizontalOrientation(this._isRtl()?"rtl":"ltr").withAllowedModifierKeys(["shiftKey"]),this._keyManager.tabOut.pipe(Object(m.a)(this._destroy)).subscribe((function(){e.panelOpen&&(!e.multiple&&e._keyManager.activeItem&&e._keyManager.activeItem._selectViaInteraction(),e.focus(),e.close())})),this._keyManager.change.pipe(Object(m.a)(this._destroy)).subscribe((function(){e._panelOpen&&e.panel?e._scrollActiveOptionIntoView():e._panelOpen||e.multiple||!e._keyManager.activeItem||e._keyManager.activeItem._selectViaInteraction()}))}},{key:"_resetOptions",value:function(){var e=this,t=Object(h.a)(this.options.changes,this._destroy);this.optionSelectionChanges.pipe(Object(m.a)(t)).subscribe((function(t){e._onSelect(t.source,t.isUserInput),t.isUserInput&&!e.multiple&&e._panelOpen&&(e.close(),e.focus())})),Object(h.a).apply(void 0,_toConsumableArray(this.options.map((function(e){return e._stateChanges})))).pipe(Object(m.a)(t)).subscribe((function(){e._changeDetectorRef.markForCheck(),e.stateChanges.next()})),this._setOptionIds()}},{key:"_onSelect",value:function(e,t){var n=this._selectionModel.isSelected(e);null!=e.value||this._multiple?(n!==e.selected&&(e.selected?this._selectionModel.select(e):this._selectionModel.deselect(e)),t&&this._keyManager.setActiveItem(e),this.multiple&&(this._sortValues(),t&&this.focus())):(e.deselect(),this._selectionModel.clear(),this._propagateChanges(e.value)),n!==this._selectionModel.isSelected(e)&&this._propagateChanges(),this.stateChanges.next()}},{key:"_sortValues",value:function(){var e=this;if(this.multiple){var t=this.options.toArray();this._selectionModel.sort((function(n,i){return e.sortComparator?e.sortComparator(n,i,t):t.indexOf(n)-t.indexOf(i)})),this.stateChanges.next()}}},{key:"_propagateChanges",value:function(e){var t;t=this.multiple?this.selected.map((function(e){return e.value})):this.selected?this.selected.value:e,this._value=t,this.valueChange.emit(t),this._onChange(t),this.selectionChange.emit(new C(this,t)),this._changeDetectorRef.markForCheck()}},{key:"_setOptionIds",value:function(){this._optionIds=this.options.map((function(e){return e.id})).join(" ")}},{key:"_highlightCorrectOption",value:function(){this._keyManager&&(this.empty?this._keyManager.setFirstItemActive():this._keyManager.setActiveItem(this._selectionModel.selected[0]))}},{key:"_scrollActiveOptionIntoView",value:function(){var e=this._keyManager.activeItemIndex||0,t=Object(l.x)(e,this.options,this.optionGroups);this.panel.nativeElement.scrollTop=Object(l.y)(e+t,this._getItemHeight(),this.panel.nativeElement.scrollTop,256)}},{key:"focus",value:function(e){this._elementRef.nativeElement.focus(e)}},{key:"_getOptionIndex",value:function(e){return this.options.reduce((function(t,n,i){return void 0!==t?t:e===n?i:void 0}),void 0)}},{key:"_calculateOverlayPosition",value:function(){var e=this._getItemHeight(),t=this._getItemCount(),n=Math.min(t*e,256),i=t*e-n,o=this.empty?0:this._getOptionIndex(this._selectionModel.selected[0]);o+=Object(l.x)(o,this.options,this.optionGroups);var a=n/2;this._scrollTop=this._calculateOverlayScroll(o,a,i),this._offsetY=this._calculateOverlayOffsetY(o,a,i),this._checkOverlayWithinViewport(i)}},{key:"_calculateOverlayScroll",value:function(e,t,n){var i=this._getItemHeight();return Math.min(Math.max(0,i*e-t+i/2),n)}},{key:"_getAriaLabel",value:function(){return this.ariaLabelledby?null:this.ariaLabel||this.placeholder}},{key:"_getAriaLabelledby",value:function(){return this.ariaLabelledby?this.ariaLabelledby:this._parentFormField&&this._parentFormField._hasFloatingLabel()&&!this._getAriaLabel()&&this._parentFormField._labelId||null}},{key:"_getAriaActiveDescendant",value:function(){return this.panelOpen&&this._keyManager&&this._keyManager.activeItem?this._keyManager.activeItem.id:null}},{key:"_calculateOverlayOffsetX",value:function(){var e,t=this.overlayDir.overlayRef.overlayElement.getBoundingClientRect(),n=this._viewportRuler.getViewportSize(),i=this._isRtl(),l=this.multiple?56:32;if(this.multiple)e=40;else{var o=this._selectionModel.selected[0]||this.options.first;e=o&&o.group?32:16}i||(e*=-1);var a=0-(t.left+e-(i?l:0)),r=t.right+e-n.width+(i?0:l);a>0?e+=a+8:r>0&&(e-=r+8),this.overlayDir.offsetX=Math.round(e),this.overlayDir.overlayRef.updatePosition()}},{key:"_calculateOverlayOffsetY",value:function(e,t,n){var i,l=this._getItemHeight(),o=(l-this._triggerRect.height)/2,a=Math.floor(256/l);return this._disableOptionCentering?0:(i=0===this._scrollTop?e*l:this._scrollTop===n?(e-(this._getItemCount()-a))*l+(l-(this._getItemCount()*l-256)%l):t-l/2,Math.round(-1*i-o))}},{key:"_checkOverlayWithinViewport",value:function(e){var t=this._getItemHeight(),n=this._viewportRuler.getViewportSize(),i=this._triggerRect.top-8,l=n.height-this._triggerRect.bottom-8,o=Math.abs(this._offsetY),a=Math.min(this._getItemCount()*t,256)-o-this._triggerRect.height;a>l?this._adjustPanelUp(a,l):o>i?this._adjustPanelDown(o,i,e):this._transformOrigin=this._getOriginBasedOnOption()}},{key:"_adjustPanelUp",value:function(e,t){var n=Math.round(e-t);this._scrollTop-=n,this._offsetY-=n,this._transformOrigin=this._getOriginBasedOnOption(),this._scrollTop<=0&&(this._scrollTop=0,this._offsetY=0,this._transformOrigin="50% bottom 0px")}},{key:"_adjustPanelDown",value:function(e,t,n){var i=Math.round(e-t);if(this._scrollTop+=i,this._offsetY+=i,this._transformOrigin=this._getOriginBasedOnOption(),this._scrollTop>=n)return this._scrollTop=n,this._offsetY=0,void(this._transformOrigin="50% top 0px")}},{key:"_getOriginBasedOnOption",value:function(){var e=this._getItemHeight(),t=(e-this._triggerRect.height)/2;return"50% ".concat(Math.abs(this._offsetY)-t+e/2,"px 0px")}},{key:"_getItemCount",value:function(){return this.options.length+this.optionGroups.length}},{key:"_getItemHeight",value:function(){return 3*this._triggerFontSize}},{key:"setDescribedByIds",value:function(e){this._ariaDescribedby=e.join(" ")}},{key:"onContainerClick",value:function(){this.focus(),this.open()}},{key:"focused",get:function(){return this._focused||this._panelOpen}},{key:"placeholder",get:function(){return this._placeholder},set:function(e){this._placeholder=e,this.stateChanges.next()}},{key:"required",get:function(){return this._required},set:function(e){this._required=Object(a.c)(e),this.stateChanges.next()}},{key:"multiple",get:function(){return this._multiple},set:function(e){if(this._selectionModel)throw Error("Cannot change `multiple` mode of select after initialization.");this._multiple=Object(a.c)(e)}},{key:"disableOptionCentering",get:function(){return this._disableOptionCentering},set:function(e){this._disableOptionCentering=Object(a.c)(e)}},{key:"compareWith",get:function(){return this._compareWith},set:function(e){if("function"!=typeof e)throw Error("`compareWith` must be a function.");this._compareWith=e,this._selectionModel&&this._initializeSelection()}},{key:"value",get:function(){return this._value},set:function(e){e!==this._value&&(this.writeValue(e),this._value=e)}},{key:"typeaheadDebounceInterval",get:function(){return this._typeaheadDebounceInterval},set:function(e){this._typeaheadDebounceInterval=Object(a.f)(e)}},{key:"id",get:function(){return this._id},set:function(e){this._id=e||this._uid,this.stateChanges.next()}},{key:"panelOpen",get:function(){return this._panelOpen}},{key:"selected",get:function(){return this.multiple?this._selectionModel.selected:this._selectionModel.selected[0]}},{key:"triggerValue",get:function(){if(this.empty)return"";if(this._multiple){var e=this._selectionModel.selected.map((function(e){return e.viewValue}));return this._isRtl()&&e.reverse(),e.join(", ")}return this._selectionModel.selected[0].viewValue}},{key:"empty",get:function(){return!this._selectionModel||this._selectionModel.isEmpty()}},{key:"shouldLabelFloat",get:function(){return this._panelOpen||!this.empty}}]),n}(Object(l.A)(Object(l.E)(Object(l.B)(Object(l.C)((function e(t,n,i,l,o){_classCallCheck(this,e),this._elementRef=t,this._defaultErrorStateMatcher=n,this._parentForm=i,this._parentFormGroup=l,this.ngControl=o})))))),I=function e(){_classCallCheck(this,e)}}}]);
function _defineProperties(n,e){for(var t=0;t<e.length;t++){var l=e[t];l.enumerable=l.enumerable||!1,l.configurable=!0,"value"in l&&(l.writable=!0),Object.defineProperty(n,l.key,l)}}function _createClass(n,e,t){return e&&_defineProperties(n.prototype,e),t&&_defineProperties(n,t),n}function _classCallCheck(n,e){if(!(n instanceof e))throw new TypeError("Cannot call a class as a function")}(window.webpackJsonp=window.webpackJsonp||[]).push([[34],{"uP/6":function(n,e,t){"use strict";t.r(e);var l=t("8Y7J"),r=function n(){_classCallCheck(this,n)},u=t("pMnS"),a=t("iInd"),i=function(){function n(){_classCallCheck(this,n)}return _createClass(n,[{key:"ngOnInit",value:function(){}}]),n}(),o=l.xb({encapsulation:0,styles:[[".dashboard-redirect-container[_ngcontent-%COMP%]{height:100%;width:100%;display:flex;justify-content:center;align-items:center}"]],data:{}});function c(n){return l.bc(0,[(n()(),l.zb(0,16777216,null,null,1,"router-outlet",[],null,null,null,null,null)),l.yb(1,212992,null,0,a.t,[a.b,l.Q,l.j,[8,null],l.h],null,null)],(function(n,e){n(e,1,0)}),null)}var s=l.vb("app-dashboard",i,(function(n){return l.bc(0,[(n()(),l.zb(0,0,null,null,1,"app-dashboard",[],null,null,null,c,o)),l.yb(1,114688,null,0,i,[],null,null)],(function(n,e){n(e,1,0)}),null)}),{},{},[]),d=t("oBbD"),b=t("pu8Q"),h=t("SCoL"),f=t("SVse"),p=t("omvX"),v=t("SxV6"),y=t("UbeY"),C=function(){function n(e,t,l){_classCallCheck(this,n),this.router=e,this.route=t,this.sessionService=l}return _createClass(n,[{key:"ngOnInit",value:function(){var n=this;this.sessionService.userType$().pipe(Object(v.a)()).subscribe((function(e){e!==y.a.GUEST?n.router.navigate(["./".concat(e)],{relativeTo:n.route}):n.router.navigate(["/login"])}))}}]),n}(),m=t("Nf/F"),g=l.xb({encapsulation:0,styles:[[".dashboard-redirect-container[_ngcontent-%COMP%]{height:100%;width:100%;display:flex;justify-content:center;align-items:center}"]],data:{}});function M(n){return l.bc(0,[(n()(),l.zb(0,0,null,null,2,"div",[["class","dashboard-redirect-container"]],null,null,null,null,null)),(n()(),l.zb(1,0,null,null,1,"mat-spinner",[["class","mat-spinner mat-progress-spinner"],["mode","indeterminate"],["role","progressbar"]],[[2,"_mat-animation-noopable",null],[4,"width","px"],[4,"height","px"]],null,null,d.d,d.b)),l.yb(2,114688,null,0,b.d,[l.l,h.a,[2,f.d],[2,p.a],b.a],null,null)],(function(n,e){n(e,2,0)}),(function(n,e){n(e,1,0,l.Ob(e,2)._noopAnimations,l.Ob(e,2).diameter,l.Ob(e,2).diameter)}))}var _,w=l.vb("app-dashboard-redirect",C,(function(n){return l.bc(0,[(n()(),l.zb(0,0,null,null,1,"app-dashboard-redirect",[],null,null,null,M,g)),l.yb(1,114688,null,0,C,[a.o,a.a,m.a],null,null)],(function(n,e){n(e,1,0)}),null)}),{},{},[]),k=t("lJxs"),j=((_=function(){function n(e,t){_classCallCheck(this,n),this.sessionService=e,this.router=t}return _createClass(n,[{key:"canActivate",value:function(n,e){var t=this;return this.sessionService.isLoggedIn$().pipe(Object(k.a)((function(n){return!!n||t.router.createUrlTree(["/login"])})))}}]),n}()).\u0275prov=l.cc({factory:function(){return new _(l.dc(m.a),l.dc(a.o))},token:_,providedIn:"root"}),_),O=function(){return t.e(17).then(t.bind(null,"G6fN")).then((function(n){return n.AdminModuleNgFactory}))},x=function(){return Promise.all([t.e(0),t.e(25)]).then(t.bind(null,"+MCA")).then((function(n){return n.CompanyModuleNgFactory}))},P=function(){return t.e(33).then(t.bind(null,"JvdY")).then((function(n){return n.CustomerModuleNgFactory}))},S=function n(){_classCallCheck(this,n)},z=t("9gLZ"),N=t("UhP/"),A=t("YEUz");t.d(e,"DashboardModuleNgFactory",(function(){return F}));var F=l.wb(r,[],(function(n){return l.Lb([l.Mb(512,l.j,l.ab,[[8,[u.a,s,w]],[3,l.j],l.y]),l.Mb(4608,f.o,f.n,[l.v]),l.Mb(1073742336,f.c,f.c,[]),l.Mb(1073742336,a.s,a.s,[[2,a.x],[2,a.o]]),l.Mb(1073742336,S,S,[]),l.Mb(1073742336,z.a,z.a,[]),l.Mb(1073742336,N.j,N.j,[A.j,[2,N.c],[2,f.d]]),l.Mb(1073742336,b.c,b.c,[]),l.Mb(1073742336,r,r,[]),l.Mb(1024,a.m,(function(){return[[{path:"",component:i,canActivate:[j],children:[{path:"",pathMatch:"full",component:C},{path:"admin",loadChildren:O},{path:"company",loadChildren:x},{path:"customer",loadChildren:P}]}]]}),[])])}))}}]);
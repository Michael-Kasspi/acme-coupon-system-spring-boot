(window.webpackJsonp=window.webpackJsonp||[]).push([[21],{"23dU":function(n,l,t){"use strict";t.r(l);var e=t("8Y7J");class o{}var c=t("pMnS"),u=t("XE/z"),i=t("Tj54"),s=t("nYR2");class r{constructor(n,l,t){this.logoutService=n,this.router=l,this.manualProgressBarService=t}ngOnInit(){this.manualProgressBarService.status=!0,this.logoutService.logout().pipe(Object(s.a)(()=>{this.manualProgressBarService.status=!1,this.router.navigate(["/home"])})).subscribe()}}var a=t("E3s/"),b=t("iInd"),g=t("yW4/"),M=e.xb({encapsulation:0,styles:[[".logging-out[_ngcontent-%COMP%]{display:flex;flex-direction:column;justify-content:center;align-items:center;min-height:100%;width:100%}.logging-out-message[_ngcontent-%COMP%]{font-weight:500;font-size:36px}.logging-out-icon[_ngcontent-%COMP%]{-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none;width:auto;text-align:center;height:160px;font-size:160px}"]],data:{}});function p(n){return e.bc(0,[(n()(),e.zb(0,0,null,null,6,"div",[["class","logging-out"]],null,null,null,null,null)),(n()(),e.zb(1,0,null,null,3,"div",[["class","logging-out-icon"]],null,null,null,null,null)),(n()(),e.zb(2,0,null,null,2,"mat-icon",[["class","mat-icon notranslate"],["color","accent"],["inline",""],["role","img"]],[[2,"mat-icon-inline",null],[2,"mat-icon-no-color",null]],null,null,u.b,u.a)),e.yb(3,9158656,null,0,i.b,[e.l,i.d,[8,null],[2,i.a],[2,e.n]],{color:[0,"color"],inline:[1,"inline"]},null),(n()(),e.Yb(-1,0,["exit_to_app"])),(n()(),e.zb(5,0,null,null,1,"h1",[["class","logging-out-message"]],null,null,null,null,null)),(n()(),e.Yb(-1,null,["Logging out"]))],(function(n,l){n(l,3,0,"accent","")}),(function(n,l){n(l,2,0,e.Ob(l,3).inline,"primary"!==e.Ob(l,3).color&&"accent"!==e.Ob(l,3).color&&"warn"!==e.Ob(l,3).color)}))}function h(n){return e.bc(0,[(n()(),e.zb(0,0,null,null,1,"app-logout",[],null,null,null,p,M)),e.yb(1,114688,null,0,r,[a.a,b.o,g.a],null,null)],(function(n,l){n(l,1,0)}),null)}var d=e.vb("app-logout",r,h,{},{},[]),v=t("9cE2"),m=t("SVse"),f=t("1O3W"),w=t("9gLZ"),j=t("iELJ"),x=t("SxV6"),O=t("lJxs"),y=t("Nf/F");let z=(()=>{class n{constructor(n,l){this.sessionService=n,this.router=l}canActivate(n,l){return this.sessionService.isLoggedIn$().pipe(Object(x.a)(),Object(O.a)(n=>!!n||this.router.createUrlTree(["/home"])))}}return n.\u0275prov=e.cc({factory:function(){return new n(e.dc(y.a),e.dc(b.o))},token:n,providedIn:"root"}),n})();class S{}var L=t("UhP/"),k=t("YEUz"),P=t("1z/I"),E=t("SCoL"),I=t("7KAL"),J=t("Dxy4");t.d(l,"LogoutModuleNgFactory",(function(){return Y}));var Y=e.wb(o,[],(function(n){return e.Lb([e.Mb(512,e.j,e.ab,[[8,[c.a,d,v.a]],[3,e.j],e.y]),e.Mb(4608,m.o,m.n,[e.v]),e.Mb(4608,f.c,f.c,[f.i,f.e,e.j,f.h,f.f,e.s,e.A,m.d,w.b,[2,m.i]]),e.Mb(5120,f.j,f.k,[f.c]),e.Mb(5120,j.c,j.d,[f.c]),e.Mb(135680,j.e,j.e,[f.c,e.s,[2,m.i],[2,j.b],j.c,[3,j.e],f.e]),e.Mb(1073742336,m.c,m.c,[]),e.Mb(1073742336,b.s,b.s,[[2,b.x],[2,b.o]]),e.Mb(1073742336,S,S,[]),e.Mb(1073742336,w.a,w.a,[]),e.Mb(1073742336,L.j,L.j,[k.j,[2,L.c],[2,m.d]]),e.Mb(1073742336,i.c,i.c,[]),e.Mb(1073742336,P.g,P.g,[]),e.Mb(1073742336,E.b,E.b,[]),e.Mb(1073742336,I.b,I.b,[]),e.Mb(1073742336,I.d,I.d,[]),e.Mb(1073742336,f.g,f.g,[]),e.Mb(1073742336,j.k,j.k,[]),e.Mb(1073742336,L.t,L.t,[]),e.Mb(1073742336,J.c,J.c,[]),e.Mb(1073742336,o,o,[]),e.Mb(1024,b.m,(function(){return[[{path:"",canActivate:[z],component:r}]]}),[])])}))}}]);
/*! project:msohu-v3, version:1.3.123, update:2014-12-15 16:53:15 */
!function(){var g_conf={cookie_id_prefix:"beans_",max_turn:60,default_timeout:3e3,url_adserver:"http://s.go.sohu.com/adgtr/",url_pv:["http://i.go.sohu.com/count/v"],url_click:["http://i.go.sohu.com/count/c"],dict_report:{adid:"aid",cont_id:"apid",impression_id:"impid",adtype:"at",monitorkey:"mkey",latency:"latcy",freq:"freq",turn:"turn",data_id:"ipos",pgid:"pgid",x:"ax",y:"ay",cx:"cx",cy:"cy",ctlk:"ctlk",ctrlt:"ctrlt",ctrln:"ctrln",c:"c",e:"e",ed:"ed",supplyid:"supplyid",ext:"ext",bucket:"bucket"}},$={get_jsonp:function(a){var b=a.url,c=a.complete,d=a.success,e=a.error,f=this,g="sohu_moblie_callback"+(Math.random()+"").substr(2,16);window[g]=function(b){"function"==typeof d&&d(b),"function"==typeof c&&c(b),window.clearTimeout(a.timeout),window[g]=void 0},b+=-1===b.indexOf("?")?"?":"&",b+="callback="+g;for(var h in a.data)b+="&"+h+"="+a.data[h];f.get_script(b),a.timeout&&(a.timeout=window.setTimeout(function(){"function"==typeof window[g]&&(window[g]=void 0),"function"==typeof e&&e(),"function"==typeof c&&c()},a.timeout))},get_script:function(a){var b=document.createElement("script"),c=document.getElementsByTagName("head")[0];b.src=a,b.onload=b.onreadystatechange=function(){(!b.readyState||/loaded|complete/.test(b.readyState))&&(b.onload=b.onreadystatechange=null,c.removeChild(b))},c.appendChild(b)},extend:function(){for(var a={},b=0;b<arguments.length;b++)for(var c in arguments[b])"constructor"!==c&&(a[c]=arguments[b][c]);return a},cookie:function(a,b,c){if(!b){var d=document.cookie.match(new RegExp("(^| )"+a+"=([^;]*)(;|$)"));return d&&d[2]}document.cookie=a+"="+escape(b)+";expires="+new Date((new Date).getTime()+60*c*60*1e3).toGMTString()},each:function(a,b,c){if(null!=a)if(a.length===+a.length)for(var d=0,e=a.length;e>d;d++)b.call(c,d,a[d],a);else for(var f in a)_.has(a,f)&&b.call(c,f,a[f],a)},using:function(a,b,c){"string"==typeof a&&(c=b,b=a,a=window);var d=b.split(".");return this.each(d,function(b){a[d[b]]=a[d[b]]||(b===d.length-1?c||{}:{}),a=a[d[b]]}),a},query:function(a,b){var c=new RegExp("(^|&)"+a+"=([^&]*)(&|$)");b=b?b.substr(b.indexOf("?")+1):window.location.search.substr(1);var d=b.match(c);return null!=d?unescape(d[2]):null}},passion={init:function(){return this.config(window.passion_config),this},ones:function(a){a=this.inst(a),a&&this.load(a)},config:function(a){a&&(g_conf=$.extend(g_conf,a))},inst:function(a){if(a&&a.length){for(var b=0,c=a.length;c>b;b++){var d=a[b];d.cont_id="beans_"+d.itemspaceid,d.status=0,d=this.format(d),a[b]=d}return a}return!1},format:function(bean){for(var pro in bean)if(/resource$|resource([0-9])$/.test(pro)){if(bean[pro].imp){try{var _imp=eval(bean[pro].imp);_imp&&(bean[pro].imp=_imp)}catch(ex){}"string"==typeof bean[pro].imp&&(bean[pro].imp=bean[pro].imp.split("|"));for(var i=0;i<bean[pro].imp.length;i++)bean[pro].imp[i]=bean[pro].imp[i].replace(/\${TS}/g,(new Date).getTime()).replace(/@local@/g,window.location.href).replace(/\${SLOTID}/g,bean.itemspaceid+"&mk="+bean.monitorkey),bean[pro].imp[i]+="&apid="+bean.cont_id+(bean.impression_id?"&impid="+bean.impression_id:"")}bean[pro].clkm&&(bean[pro].clkm=bean[pro].clkm.replace(/\${TS}/g,(new Date).getTime()).replace(/@local@/g,window.location.href).replace(/\${SLOTID}/g,bean.itemspaceid+"&mk="+bean.monitorkey),bean[pro].clkm+=(-1===bean[pro].clkm.indexOf("?")?"?":"&")+"apid="+bean.cont_id+(bean.impression_id?"&impid="+bean.impression_id:""))}return bean},load:function(a){for(var b=0,c=a.length;c>b;b++){var d=a[b];d.resource?(this.paint(d),this.attach(d)):(d.monitorkey=void 0,this.report("pv",d))}},special:function(a){var b=document.getElementById(a.cont_id);if(b){var c=$.using(a,"special.dict");if("picturetxt"===a.form){var d=a[c.picture||"resource"],e=a[c.txt||"resource1"];d&&e&&(b.innerHTML='<a href="'+d.click+'" target="_blank"><img src="'+d.file+'" border="0" style="max-width:100%; max-height:auto;"/></a><div class="topic-title"><p>'+e.text+"</p></div>",a.status=1,this.report(d.imp),this.report(e.imp),this.report("pv",a))}}},paint:function(a){if(a){var b=a.resource,c=document.getElementById(a.cont_id);if(b&&c){var d=a.form;if(d)this.special(a);else{switch(b.type){case"text":c.innerHTML='<a href="javascript:;" data-url="'+b.click+'" >'+b.text+"</a>",a.status=1;break;case"image":c.innerHTML='<a href="javascript:;" data-url="'+b.click+'"><img src="'+b.file+'" border="0" style="max-width:100%; max-height:auto;" /></a>',a.status=1;break;case"iframe":var e="",f=$.query("w",b.file),g=$.query("h",b.file);b.file+=(-1===b.file.indexOf("?")?"?":"&")+"clkm="+encodeURIComponent(this.get_url(a)),e=f&&g?'<iframe style="width:'+f+"px; height:"+g+'px;" frameborder="0" marginwidth="0" marginheight="0" margin="0 auto;" scrolling="no" src="'+b.file+'"></iframe>':'<iframe style="max-width:100%; max-height:auto;" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" src="'+b.file+'"></iframe>',c.innerHTML=e,a.status=1}1===a.status&&(this.report(b.imp),this.report("pv",a))}}}},get_url:function(a){var b=g_conf.url_click,c="",d=g_conf.dict_report;if(a)for(var e in d)a[e]&&(c+=(c&&"&")+(d[e]+"="+a[e]));return c&&(b+=(-1===b.indexOf("?")?"?":"&")+c),b},attach:function(a){var b=this;if(a.status){var c=document.getElementById(a.cont_id);c.style.display="block",c.addEventListener("mousedown",function(){b.report("click",a),b.report(a.resource&&a.resource.clkm);var d=c.firstChild;if(d&&"A"===d.tagName)var e=d.getAttribute("data-url"),f=setTimeout(function(){f=null,window.location.href=e},300)},!1)}},report:function(a,b){if(a){var c="string"==typeof a?g_conf["url_"+a]||new Array(a):a,d="",e=g_conf.dict_report;if(b)for(a in e)b[a]&&(d+=(d&&"&")+(e[a]+"="+b[a]));for(var f=0;f<c.length;f++){var g=c[f];if(-1!==g.indexOf("http")){d&&(g+=(-1===g.indexOf("?")?"?":"&")+d);var h=Math.random(),i=window["IMG_"+h]=new Image;i.onload=i.onerror=function(){i=null,window["IMG_"+h]=null},i.src=g}}}},get_turn:function(a){if(!this.turn){var b=g_conf.cookie_id_prefix+"turn",c=parseInt($.cookie(b)||parseInt(Math.random()*g_conf.max_turn+1,10),10);this.turn=c;var d=c+1;d>g_conf.max_turn&&(d=1),$.cookie(b,d,{path:"/",expires:1})}return a?(this.turn-1)%a+1:this.turn}}.init(),Data_request=function(a,b){this.param=a,this.callback=b,this.data=[]};Data_request.prototype={get:function(){var a=this.param;if(a.itemspaceid){var b={};b[a.itemspaceid]=a,a=this.param=b}for(var c in a){var d,e=a[c].adsrc;a[c].itemspaceid=c,e>=200&&(d=this.get_from_cpds(a[c])),e>200&&(d||(a[c].adsrc=e-200,this.get_from_ads(a[c]))),200>e&&this.get_from_ads(a[c])}this.check_complete()},get_from_cpds:function(a){if(a.itemspaceid){var b=1,c=null,d=this.data,e=a.itemspaceid,f=!1,g=window.AD_DATA||{};return/^\d{4,8}$/.test(e)&&(a.status=0,a.turn||(a.turn=passion.get_turn(a.max_turn)),b=a.turn-1,c=g[e]&&g[e][b]&&g[e][b].data||{},f=g[e]&&g[e][b]&&g[e][b].isloc,c=f&&(CONFIG.IP||CONFIG.SOIP)?c[CONFIG.IP]||c[CONFIG.SOIP.substr(0,6)]||c[CONFIG.SOIP.substr(0,4)]||c.DEFAULT:c.DEFAULT)?(d.push($.extend({},a,c)),!0):void 0}},get_from_ads:function(a){var b=this;a||(a=this.param);var c=a.query||{};if(a.itemspaceid){if(c.itemspaceid=a.itemspaceid,a.status=-1,a.adps)a.width=parseInt(a.adps.substr(0,a.adps.length-4),10),a.height=parseInt(a.adps.substr(a.adps.length-4),10);else{if(!a.width||!a.height)return;var d=(a.height+"").length;a.adps=c.adps=a.width+(1<<(d>=4?0:4-d)).toString(2).substr(1)+a.height}c.adps||(c.adps=a.adps),c.adsrc||(c.adsrc=a.adsrc),c.turn||(c.turn=a.turn||passion.get_turn(a.max_turn)),c.apt=a.supplyid=c.apt||4,a.start_time=(new Date).getTime(),this.param[a.itemspaceid]=a,$.get_jsonp({url:g_conf.url_adserver,timeout:g_conf.default_timeout,data:c,complete:function(){b.complete(a)},success:function(c){b.success(c,a)}})}},complete:function(a){this.param[a.itemspaceid].status=0,this.check_complete()},success:function(a,b){a&&a.length?(a[0].by_server="1",a[0].end_time=(new Date).getTime(),this.data.push($.extend({},b,a[0]))):this.data.push(b),this.check_complete()},check_complete:function(){var a=this.param,b=this.data;for(var c in a)if(-1===a[c].status)return;this.callback.call(this,b)}},window.passion={ones:function(a){var b=Object.prototype.toString.call(a);if("[object Array]"===b)passion.ones(a);else if("[object Object]"===b){var c=new Data_request(a,function(a){passion.ones(a)});c.get()}},config:function(a){return passion.config(a)}}}();
//# sourceMappingURL=ad_js.map
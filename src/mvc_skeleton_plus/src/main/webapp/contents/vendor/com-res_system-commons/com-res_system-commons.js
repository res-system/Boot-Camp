/**
 * @file com-res_system-commons.js
 * @author res.
 * @version 1.1.2 (2018.12.07)
 */
var $ReC={};
(function(){'use strict'; $ReC.isIE=function(){var ie=/*@cc_on!@*/false;if(ie){return true;}else{return false;}};
if($ReC.isIE()){alert('Doesn\'t correspond to the old IE. Please use the HTML5 browser.');return;}
/* basic */
$ReC.doc    =document;
$ReC.id     =function(id){if(id){return $ReC.doc.getElementById(id);}else{return null;}};
$ReC.name   =function(nm){var l=$ReC.names(nm);if(l.length>0){return l[0];}else{return null;}};
$ReC.names  =function(nm){if($ReC.isStrBlk(nm)){return new Array();}else{return $ReC.doc.getElementsByName(nm);}};
$ReC.find   =function(sr){if($ReC.isStrBlk(sr)){return null;}else{return $ReC.doc.querySelector(sr);}};
$ReC.findAll=function(sr){if($ReC.isStrBlk(sr)){return new Array();}else{return $ReC.doc.querySelectorAll(sr);}};
/* check */
$ReC.getType  =function(o){return Object.prototype.toString.call(o);};
$ReC.isEmpty  =function(v){if(!v){if(v===0){return false;}if(v===false){return false;}return true;}else{return false;}};
$ReC.isStrBlk =function(v){if($ReC.isStr(v)){return $ReC.isEmpty(v.trim());}else{return true;}};
$ReC.isAryBlk =function(v){if($ReC.isArray(v)){return (v.length<=0);}else{return true;}};
$ReC.isObj    =function(t){return ($ReC.getType(t)==='[object Object]');};
$ReC.isArray  =function(t){return ($ReC.getType(t)==='[object Array]'||Array.isArray(t));};
$ReC.isHTML   =function(t){return ($ReC.getType(t).substr(0,12)==='[object HTML');};
$ReC.isNodes  =function(t){return ($ReC.getType(t)==='[object NodeList]');};
$ReC.isStr    =function(t){return ($ReC.getType(t)==='[object String]');};
$ReC.isNum    =function(t){return ($ReC.getType(t)==='[object Number]');};
$ReC.isBool   =function(t){return ($ReC.getType(t)==='[object Boolean]');};
$ReC.isDate   =function(t){return ($ReC.getType(t)==='[object Date]');};
$ReC.isFunc   =function(t){return ($ReC.getType(t)==='[object Function]');};
$ReC.isTag    =function(e,nm){if(!$ReC.isHTML(e)||$ReC.isStrBlk(nm)){return false;}else{return (('tagName' in e)&&e.tagName===nm.toUpperCase());}};
$ReC.isIType  =function(e,nm){if(!$ReC.isTag(e,'INPUT')||$ReC.isStrBlk(nm)){return false;}else{return (('type' in e)&&e.type===nm.toLowerCase());}};
/* event */
$ReC.event    =function(e,nm,fn){if($ReC.isHTML(e)){e.addEventListener(nm,fn,false);}else if($ReC.isNodes(e)){for(var i=0,imx=e.length;i<imx;i++){$ReC.event(e[i],nm,fn);}}};
$ReC.action   =function(fm,ul,md){if(!$ReC.isTag(fm,'FORM')){return false;}if(!$ReC.isStrBlk(ul)){fm.action=ul;}if(!$ReC.isStrBlk(md)){fm.method=md;}fm.submit();return true;};
$ReC.ajax     =function(ul,da,fn,md){if(!$ReC.isStrBlk(ul)){var rq=new XMLHttpRequest();rq.onreadystatechange=function(){if(fn){fn(rq);}};if($ReC.isStrBlk(md)){md='POST';}rq.open(md,ul,true);if(md==='POST'){rq.setRequestHeader('Content-Type','application/x-www-form-urlencoded');}rq.send(da);}};
$ReC.isAjaxOK =function(rq){if(!$ReC.isEmpty(rq)&&('readyState' in rq)&&rq.readyState===4){if(('status' in rq)&& (rq.status==200||rq.status==304)){return true;}}return false;};
$ReC.isAjaxNG =function(rq){if(!$ReC.isEmpty(rq)&&('readyState' in rq)&&rq.readyState===4){if(('status' in rq)&&!(rq.status==200||rq.status==304)){return true;}}return false;};
/* html */
$ReC.html     =function(e,hl,ps){if(hl&&$ReC.isStr(hl)){if($ReC.isHTML(e)){if(ps&&$ReC.isStr(ps)){e.insertAdjacentHTML(ps,hl);}else{e.innerHTML=hl;}}else if($ReC.isNodes(e)&&e.length>0){for(var i=0,imx=e.length;i<imx;i++){$ReC.html(e[i],hl,ps);}}return;}else{if($ReC.isHTML(e)){return (e.innerHTML)?e.innerHTML:'';}else if($ReC.isNodes(e)&&e.length>0){return $ReC.html(e[0]);}return '';}};
$ReC.val      =function(e,v){if(v){if($ReC.isHTML(e)&&('value' in e)){e.value=v;}else if($ReC.isNodes(e)&&e.length>0){for(var i=0,imx=e.length;i<imx;i++){$ReC.val(e[i],v);}}return;}else{if($ReC.isHTML(e)&&('value' in e)){return e.value;}else if($ReC.isNodes(e)&&e.length>0){return e[0].value;}return '';}};
$ReC.checked  =function(e,b){if($ReC.isBool(b)){if($ReC.isHTML(e)&&('checked' in e)){e.checked=b;}else if($ReC.isNodes(e)&&e.length>0){for(var i=0,imx=e.length;i<imx;i++){$ReC.checked(e[i],b);}}return;}else{if($ReC.isHTML(e)&&('checked' in e)){return e.checked;}else if($ReC.isNodes(e)&&e.length>0){return e[0].checked;}return false;}};
$ReC.display  =function(e,b){if($ReC.isBool(b)){if($ReC.isHTML(e)){if(b){e.style.display='block';}else{e.style.display='none';}}else if($ReC.isNodes(e)&&e.length>0){for(var i=0,imx=e.length;i<imx;i++){$ReC.display(e[i],b);}}return;}else{if($ReC.isHTML(e)){return (e.style.display==='block');}else if($ReC.isNodes(e)&&e.length>0){return $ReC.display(e[0]);}return false;}};
$ReC.visible  =function(e,b){if($ReC.isBool(b)){if($ReC.isHTML(e)){if(b){e.style.visibility='visible';}else{e.style.visibility='hidden';}}else if($ReC.isNodes(e)&&e.length>0){for(var i=0,imx=e.length;i<imx;i++){$ReC.visible(e[i],b);}}return;}else{if($ReC.isHTML(e)){return (e.style.visibility==='visible');}else if($ReC.isNodes(e)&&e.length>0){return $ReC.visible(e[0]);}return false;}};
$ReC.makeTag  =function(tg){return $ReC.doc.createElement(tg);};
$ReC.addHidden=function(fm,i,nm,v){var e=$ReC.makeTag('INPUT');e.type='hidden';e.id=i;e.name=nm;e.value=v;fm.appendChild(e);};
$ReC.inputTab =function(e){if($ReC.isHTML(e)){$ReC.event(e,'keydown',function(ev){var el,en,st,v;if(!e.disabled&&(!ev.shiftKey&&ev.keyCode===9)){el=ev.target;st=el.selectionStart;en=el.selectionEnd;v=el.value;if(v.length!==st){if(ev.preventDefault){ev.preventDefault();}el.value=''+(v.substring(0,st))+'\t'+(v.substring(en));el.selectionStart=el.selectionEnd=st+1;return false;}}});}else if($ReC.isNodes(e)){for(var i=0,imx=e.length;i<imx;i++){$ReC.inputTab(e[i]);}}};
/* format */
$ReC.formatDate =function(dt,ft){if($ReC.isDate(dt)){ft=(ft)?ft:'YYYY-MM-DD hh:mm:ss';ft=ft.replace(/YYYY/g,dt.getFullYear());ft=ft.replace(/MM/g,('0'+(dt.getMonth()+1)).slice(-2));ft=ft.replace(/DD/g,('0'+dt.getDate()).slice(-2));ft=ft.replace(/hh/g,('0'+dt.getHours()).slice(-2));ft=ft.replace(/mm/g,('0'+dt.getMinutes()).slice(-2));ft=ft.replace(/ss/g,('0'+dt.getSeconds()).slice(-2));return ft;}if($ReC.isStr(dt)){return $ReC.formatDate($ReC.toDate(dt),ft);}return '';};
$ReC.formatNum  =function(nu){if($ReC.isStr(nu)){return $ReC.formatNum($ReC.toFloat(nu));}if($ReC.isNum(nu)){var p=String(nu).split('.');p[0]=p[0].replace(/(\d)(?=(\d\d\d)+(?!\d))/g,'$1,');return p.join('.');}return '';};
/* date */
$ReC.addDate  =function(dt,nu,tp){if(!$ReC.isDate(dt)||!$ReC.isNum(nu)){return dt}switch(tp){case 'Y':dt.setFullYear(dt.getFullYear()+nu);break;case 'M':dt.setMonth(dt.getMonth()+nu);break;case 'h':dt.setHours(dt.getHours()+nu);break;case 'm':dt.setMinutes(dt.getMinutes()+nu);break;case 's':dt.setSeconds(dt.getSeconds()+nu);break;default :dt.setDate(dt.getDate()+nu);}return dt;};
$ReC.dateDiff =function(dt1,dt2,tp){if(!$ReC.isDate(dt1)||!$ReC.isDate(dt2)){return 0}var df=dt2.getTime()-dt1.getTime();switch(tp){case 'Y':return dt2.getFullYear()-dt1.getFullYear();break;case 'M':return (dt2.getFullYear()*12+dt2.getMonth())-(dt1.getFullYear()*12+dt1.getMonth());break;case 'h':return ~~(df/(60*60*1000));break;case 'm':return ~~(df/(60*1000));break;case 's':return ~~(df/1000);break;default :return ~~(df/(24*60*60*1000));}};
/* string */
$ReC.splitCl  =function(tx){if(!$ReC.isStrBlk(tx)){tx=tx.trim();return tx.split(/\r\n|\r|\n/);}else{return new Array();}};
$ReC.splitCm  =function(tx){if(!$ReC.isStrBlk(tx)){tx=tx.trim();return tx.split(/,/);         }else{return new Array();}};
$ReC.splitTb  =function(tx){if(!$ReC.isStrBlk(tx)){tx=tx.trim();return tx.split(/\t/);        }else{return new Array();}};
$ReC.splitSp  =function(tx){if(!$ReC.isStrBlk(tx)){tx=tx.trim();return tx.split(/\x20+/);     }else{return new Array();}};
$ReC.toUCase  =function(tx){if(!$ReC.isStrBlk(tx)){tx=tx.trim();tx=tx.toUpperCase();} return tx;};
$ReC.toLCase  =function(tx){if(!$ReC.isStrBlk(tx)){tx=tx.trim();tx=tx.toLowerCase();} return tx;};
$ReC.toSnakeCase  =function(tx){if(!$ReC.isStrBlk(tx)){tx=tx.trim();tx=tx.replace(/[A-Z]/g,function(m){return '_'+m.toLowerCase();});tx=tx.replace(/ /g,'_');tx=tx.replace(/__/g,'_');if(tx.charAt(0)==='_'){tx=tx.substr(1);}} return tx;};
$ReC.toCamelCase  =function(tx){if(!$ReC.isStrBlk(tx)){tx=tx.trim();if(!(tx.indexOf('_')>=0 || tx.indexOf(' ')>=0)){tx=$ReC.toSnakeCase(tx);}tx=tx.toLowerCase();tx=tx.replace(/ /g,'_');tx=tx.replace(/_./g,function(m){return m.charAt(1).toUpperCase();});} return tx;};
$ReC.toPascalCase =function(tx){if(!$ReC.isStrBlk(tx)){tx=tx.trim();tx=$ReC.toCamelCase(tx);tx=tx.charAt(0).toUpperCase()+tx.substr(1);} return tx;};
$ReC.toCapitalize =function(tx){if(!$ReC.isStrBlk(tx)){tx=tx.trim();tx=tx.charAt(0).toUpperCase()+tx.substr(1);} return tx;};
$ReC.toInt  =function(tx,df){if(tx){try{var n=parseInt(tx);if(!isNaN(n)){return n;}}catch(e){}}if($ReC.isNum(df)){return df;} return null;};
$ReC.toFloat=function(tx,df){if(tx){try{var n=parseFloat(tx);if(!isNaN(n)){return n;}}catch(e){}}if($ReC.isNum(df)){return df;} return null;};
$ReC.toDate =function(tx,df){if(tx){try{var d=new Date(tx);if(d.toString()!=="Invalid Date"){return d;}}catch(e){}}if($ReC.isDate(df)){return df;} return null;};
$ReC.parseJSON    =function(tx){if(!$ReC.isStrBlk(tx)){try{return JSON.parse(tx);}catch(e){}} return null;};
$ReC.escapeHTML   =function(tx){if(!$ReC.isStrBlk(tx)){tx=tx.replace(/[ &'`"<>]/g,function(match){return {' ':'&nbsp;','&':'&amp;',"'":'&#x27;','`':'&#x60;','"':'&quot;','<':'&lt;','>':'&gt;'}[match]});return tx;} return '';};
$ReC.escapeHTMLCL =function(tx){if(!$ReC.isStrBlk(tx)){tx=tx.replace(/[ &'`"<>]/g,function(match){return {' ':'&nbsp;','&':'&amp;',"'":'&#x27;','`':'&#x60;','"':'&quot;','<':'&lt;','>':'&gt;'}[match]});return tx.replace(/\r?\n/g,'<br />');} return '';};
})();

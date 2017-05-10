var POCOL={CHAR_ENCODING:"UTF-8",SERVICE_NAME:"_serviceName",USER_TOKEN:"_userToken",FILE_NAME:"_fileName",FILE_NAME_FOR_LOGS:"_logs",MIME_TYPE:"_mimeType",DISCARD_FILE:"_discard",REQUEST_STATUS:"_requestStatus",FILE_TOKEN:"_fileToken",TRACE_FIELD_NAME:"_trace",MESSAGES:"_messages",STATUS_OK:"ok",STATUS_NO_LOGIN:"noLogin",STATUS_ERROR:"error",SERVICE_EXECUTION_TIME:"_serviceExecutionTime",MESSAGE_SUCCESS:"success",MESSGAE_INFO:"info",MESSAGE_WARNING:"warning",MESSAGE_ERROR:"error",TABLE_ACTION_FIELD_NAME:"_saveAction",TABLE_ACTION_ADD:"add",TABLE_ACTION_MODIFY:"modify",TABLE_ACTION_DELETE:"delete",TABLE_ACTION_SAVE:"save",EQUAL:"=",NOT_EQUAL:"!=",LESS:"<",LESS_OR_EQUAL:"<=",GREATER:">",GREATER_OR_EQUAL:">=",LIKE:"~",STARTS_WITH:"^",BETWEEN:"><",IN_LIST:"@",TO_FIELD_SUFFIX:"To",COMPARATOR_SUFFIX:"Comparator",SORT_COLUMN_NAME:"_sortColumns",SORT_ORDER:"_sortOrder",SORT_ORDER_ASC:"asc",SORT_ORDER_DESC:"desc",PAGINATION_SERVICE:"_p",PAGE_SIZE_SUFFIX:"PageSize",TOTAL_COUNT_SUFFIX:"TotalCount",PAGINATION_TABLE:"_tableName",PAGINATION_SIZE:"_pageSize",PAGINATION_PAGE_NUMBER:"_pageNumber",LIST_SERVICE_KEY:"_key",SUGGEST_STARTING:"_matchStarting",ALL_FIELDS:"_allFields",COL_MARKER:"__",IDX_MARKER:"i",DATA_TABLE:"data-table",DATA_ROW:"data-row",HAS_CHILDREN:"data-has-children",HIDE_IF_NO_DATA:"data-hide-if-no-data",LAST_JSON:"_lastJson",LOCAL_STORAGE_NAME:"_localData",LOCAL_SERVICES:"_localServices",LOCAL_RESPONSES:"_localResponses",};var Simplity=(function(){var Q=function(X){if(!X||!X.replace){return X}return X.replace(/&/g,"&amp;").replace(/</g,"&lt;")};var y=function(X){alert("ERROR\n"+X)};var j=function(X){alert("Warning\n"+X)};var h=function(Y,X){console.log(Y)};var S=function(Z,Y,X){if(Z&&Y){y=Z;j=warningFn}else{reportError("You need to specify both errorFn and warningFn for re-plumbing logging with pipeLogging() method");return}if(X){h=X}};var C=function(X){alert(X)};var a=function(Z){if(!Z||!Z.length){alert("There are no messages")}var Y=[];for(var X=0;X<Z.length;X++){var aa=Z[X];if(aa.messageType){Y.push(aa.messageType.toUpperCase());Y.push("\n")}if(aa.name){Y.push(aa.name);Y.push(" : ")}Y.push(aa.text);Y.push("\n\n")}C(Y.join(""))};var m=function(X){C=X};var d=function(X){a=X};var R=function(ae,ac){if(Array.isArray(ae)){h("Simplity funciton pushDataToPage() requires that the json is an object-map, but not an array. Data not pushed to page");return}if(!ac){ac=window.document}var Z=W(ac);for(var aa in ae){if(aa.indexOf("_")===0){h("Ignoring the reserved attribute "+aa);continue}var X=ae[aa];var af;if(X===null||Array.isArray(X)){if(!X){X=[]}af=ac.getElementById(POCOL.COL_MARKER+aa+POCOL.COL_MARKER);if(af){O(af,X||[],aa);continue}af=ac.getElementById(aa);if(af&&af.getAttribute(POCOL.DATA_TABLE)){F(af,X||[],aa);continue}var ag=Z&&Z[aa];if(ag){for(var Y=ag.length-1;Y>=0;Y--){u(ag[Y],X||[])}continue}if(X){h("No destination found for table "+aa);continue}}af=ac.getElementById(aa);if(af){b(af,aa,X);continue}var ad=ac.getElementById(aa+"-true");var ab=ac.getElementById(aa+"-false");if(ad||ab){A(ad,ab,X);continue}}};var u=function(ab,aa){var Z=ab.options;var ad=null;if(Z&&Z.length){Z=Z[ab.selectedIndex];ad=Z&&Z.value}var Y=[];var ae=aa.length;for(var X=0;X<ae;X++){var ac=aa[X];Y.push('<option value="');Y.push(ac.key);if(ad&&ad==ac.key){ad=null;Y.push('" selected="selected">')}else{Y.push('">')}Y.push(Q(ac.value));Y.push("</option>")}ab.innerHTML=Y.join("")};var O=function(ai,ae,ad){var X=ae.length;ai.style.display="none";var af=ai.parentNode;if(!X){h("No data in table "+ad);af.innerHTML=ai.outerHTML;return}af.style.display="none";var ag=[ai.outerHTML];ai.style.display="";var Y=ai.outerHTML.split(POCOL.COL_MARKER);var aa=Y.length;if(aa%2!==1){y("Element for table "+ad+" does not follow the convention proerly. Look for pairs of "+POCOL.COL_MARKER);return}for(var Z=0;Z<X;Z++){var ah=ae[Z];ag.push(Y[0]);ag.push(ad+"_"+Z);ag.push(Y[2]);var ac=3;while(ac<aa){var ab=Y[ac];ac++;if(ab==="i"){ag.push(Z+1)}else{if(ah.hasOwnProperty(ab)){ag.push(x(ah[ab]))}else{h("No value for column "+ab+". cell value set to empty string")}}ag.push(Y[ac]);ac++}}af.innerHTML=ag.join("");ai.style.display="none";af.style.display=""};var F=function(ad,aa,Y){var X=E(ad);var ab=X.parentNode;if(!aa||!aa.length){if(ad.hasAttribute(POCOL.HIDE_IF_NO_DATA)){h("No data in table "+Y+" and hence we are hiding the table element");ad.style.display="none"}else{h("No data in table "+Y);X.style.display="none";ab.innerHTML=X.outerHTML}return}var ac=n(ad,Y,false);var Z=[];P(ac,{},aa,Z,"");ab.innerHTML=Z.join("");if(ad.hasAttribute(POCOL.HIDE_IF_NO_DATA)){ad.style.display=""}return};var E=function(Y){var Z=Y.firstChild;while(Z){if(Z.hasAttribute){if(Z.hasAttribute(POCOL.DATA_ROW)){return Z}var X=E(Z);if(X){return X}}Z=Z.nextSibling}return null};var i="___";var n=function(ad,Y,ae){var X=E(ad);if(!X){y("Element "+ad.id+" is marked as data-table, but it does not have a child ele marked as data-row");return null}var ac=null;ac=[];var aa=null;if(ae){var ab=X.parentNode;ab.insertBefore(document.createTextNode(i),X);ab.removeChild(X);var Z=ad.outerHTML.split(i);if(Z.length!=2){y("we assume that you have no need to use three underscores together anywhere, but found one such instance in your html");return null}f(ac,Z[0]);aa=Z[1]}X.style.display="none";ac.push({html:X.outerHTML});X.style.display="";ac.startAt=ac.length;if(ad.hasAttribute(POCOL.HAS_CHILDREN)){T(ac,X)}else{f(ac,X.outerHTML)}ac.endBefore=ac.length;if(aa){f(ac,aa)}return ac};var f=function(ab,aa){var Z=aa.split(POCOL.COL_MARKER);var ac=Z.length;if(ac%2!==1){y("We assume that you use double underscore as delimiter for marking tabl elements, like __colName__, Since this is ot true, we are unabel to set data to table.");return}ab.push({html:Z[0]});for(var Y=1;Y<ac;Y++){var X=Z[Y];if(X===POCOL.IDX_MARKER){ab.push({id:true})}else{ab.push({col:X})}Y++;ab.push({html:Z[Y]})}return};var T=function(ad,Z){var ac=Z.cloneNode();ac.innerHTML=i;var X=ac.outerHTML.split(i);if(X.length!=2){y("Our algorithm for setting data to table assumes that your html has no triple underscores. Please remove them from your html");return}var ab=[X[0]];ac=Z.firstChild;while(true){if(!ac){if(ab.length){f(ad,ab.join(""))}break}if(!ac.getAttribute){ab.push(ac.nodeValue)}else{var Y=ac.getAttribute(POCOL.DATA_TABLE);if(!Y){ab.push(ac.outerHTML)}else{if(ab.length){f(ad,ab.join(""));ab=[]}var aa=n(ac,Y,true);ad.push({child:Y,parts:aa})}}ac=ac.nextSibling}ad.push({html:X[1]});return};var P=function(ac,ah,Y,af,ad){var ag=ac.startAt;for(var ae=0;ae<ag;ae++){af.push(k(ac[ae],ah,ad))}var ag=ac.endBefore;var Z=!Y?0:Y.length;for(var ab=0;ab<Z;ab++){var ai=Y[ab];var X=ad+"_"+ab;for(var ae=ac.startAt;ae<ag;ae++){var aa=ac[ae];if(aa.child){P(aa.parts,ai,ai[aa.child],af,X)}else{af.push(k(aa,ai,X))}}}ag=ac.length;for(var ae=ac.endBefore;ae<ag;ae++){af.push(k(ac[ae],ah,ad))}};var k=function(Y,aa,Z){var X=Y.html;if(X){return X}if(Y.id){return Z}X=Y.col;if(aa&&aa.hasOwnProperty(X)){return x(aa[X])}return""};var x=function(X){if(X&&X.toLocaleDateString){X=X.toLocaleDateString()}else{X=Q(X)}return X};var W=function(ae){var X=ae.getElementsByTagName("select");var ab=0;var Z={};for(var aa=X.length-1;aa>=0;aa--){var ad=X[aa];var Y=ad.getAttribute(POCOL.DATA_TABLE);if(Y){var ac=Z[Y];if(!ac){ac=Z[Y]=[]}ac.push(ad);ab++}}if(ab){return Z}return null};var p=function(Z){if(!Array.isArray(Z)){h("Simplity function downloadCSV() requires that the json to be an array. Data not pushed to page");return}var X="";for(var aa=0;aa<Z.length;aa++){var ad="";for(var Y in Z[aa]){ad+='"'+Z[aa][Y]+'",'}ad.slice(0,ad.length-1);X+=ad+"\r\n"}for(var aa=0;aa<Z.length;aa++){var ad="";for(var Y in Z[aa]){ad+='"'+Z[aa][Y]+'",'}ad.slice(0,ad.length-1);X+=ad+"\r\n"}if(X==""){alert("Invalid data");return}var ae="download";var ac="data:text/csv;charset=utf-8,"+escape(X);var ab=document.createElement("a");ab.href=ac;ab.style="visibility:hidden";ab.download=ae+".csv";document.body.appendChild(ab);ab.click();document.body.removeChild(ab)};var A=function(Z,X,Y){if(X){X.style.display==Z?"":"none"}if(Y){Y.style.display==Z?"none":""}};var b=function(ad,ae,ac){if(ac==null){fieldVaue=""}var Y=ad.tagName.toLowerCase();if(Y==="input"){if(ad.type.toLowerCase()==="checkbox"){ad.checked=ad.value?true:false}else{ad.value=ac}return}if(Y==="select"){if(ad.multiple){var ab=getVals(ac);var aa=ad.firstChild;while(aa){if(ab[aa.value]){ad.setAttribute("selected","selected")}else{ad.removeAttribute("selected")}aa=aa.nextSibling}}else{ad.value=ac}return}if(ad.hasAttribute("data-value")){ad.setAttribute("data-value",ac);return}if(ad.hasAttribute("data-radio")){var X=ad.getElementsByTagName("input");if(!X.length){h("we found tag for "+ae+" as data-radio but it does not have radio child nodes");return}for(var Z=X.length-1;Z>=0;Z--){var aa=X[Z];aa.checked=aa.value==ac}return}if(ad.hasAttribute("data-checkbox")){var ab=getVals(ac);var X=ad.getElementsByTagName("input");if(!X.length){h("we found tag for "+ae+" but it does not have check-box child nodes");return}for(var Z=X.length-1;Z>=0;Z--){var aa=X[Z];aa.checked=ab[aa.value]}return}ad.textContent=ac};var w="POST";var G="a._s";var H="a._f";var K="a._i";var J="a._o";var q=12000;var c=null;var L=function(Z,aa,X,Y){X=X||R;Y=Y||a;var ad=new XMLHttpRequest();ad.onreadystatechange=function(){if(this.readyState!="4"){return}var af={};if(ad.responseText){af=JSON.parse(ad.responseText)}if(ad.status&&ad.status!=200){h("HTTP error from server (non-200)\n"+ad.responseText);Y(o("Server or the communication infrastructure has failed to respond."));return}var ae=af[POCOL.REQUEST_STATUS]||POCOL.STATUS_OK;if(ae==POCOL.STATUS_OK){X(af)}else{if(af[POCOL.MESSAGES]){Y(af[POCOL.MESSAGES])}else{Y(o("Login failed"))}}};ad.ontimeout=function(){Y(o("Sorry, there seem to be some red-tapism on the server. Can't wait any more for a decision. Giving up."))};try{ad.open(w,K,true);ad.timeout=q;ad.setRequestHeader("Content-Type","text/html; charset=utf-8");var ac=Z;if(aa){ac+=" "+aa}ad.setRequestHeader(POCOL.USER_TOKEN,btoa(ac));ad.send("")}catch(ab){Y(o("Unable to connect to server. Error : "+ab.message))}};var r=function(){var Y=new XMLHttpRequest();try{Y.open(w,J,true);Y.setRequestHeader("Content-Type","text/html; charset=utf-8");Y.send("")}catch(X){a(o("Unable to connect to server. Error : "+X))}};var V=function(Z,aa,X,Y){X=X||R;Y=Y||a;if(!Z){h("No service");Y(o("No serviceName specified"));return}h("Service "+Z+" invoked");var ac=new XMLHttpRequest();ac.onreadystatechange=function(){if(this.readyState!="4"){return}var ae={};if(ac.responseText){try{ae=JSON.parse(ac.responseText)}catch(ag){h("Response is not json. response text is returned instead of js object....");ae=ac.responseText}}if(ac.status&&ac.status!=200){h("HTTP error from server (non-200)\n"+ac.responseText);Y(o("Server or the communication infrastructure has failed to respond."));return}var ad=ae[POCOL.REQUEST_STATUS]||POCOL.STATUS_OK;if(ad==POCOL.STATUS_OK){window[POCOL.LAST_JSON]=ae;h("Json saved as "+POCOL.LAST_JSON);X(ae);return}if(ad==POCOL.STATUS_NO_LOGIN){if(c){h("Login required. invoking relogin");c(Z,aa,X,Y);return}Y(o("This service requires a valid login. Please login and try again."));return}var af=ae[POCOL.MESSAGES];if(!af){af=o("Server reported a failure, but did not specify any error text.")}Y(af)};ac.ontimeout=function(){h("time out");Y(o("Sorry, there seem to be some red-tapism on the server. giving-up"))};try{ac.open(w,G,true);ac.timeout=q;ac.setRequestHeader("Content-Type","text/html; charset=utf-8");ac.setRequestHeader(POCOL.SERVICE_NAME,Z);ac.send(aa)}catch(ab){h("error during xhr : "+ab.message);Y(o("Unable to connect to server. Error : "+ab.message))}};var o=function(X){return[{messageType:"error",text:X}]};var l=function(X){var Y={};Y[POCOL.REQUEST_STATUS]=POCOL.STATUS_ERROR;Y[POCOL.MESSAGES]=o(X);return Y};var v=function(){var X={};X[POCOL.REQUEST_STATUS]=POCOL.STATUS_OK;X[POCOL.MESSAGES]=[];return X};var B=function(aa,ac,X,Z){X=X||R;Z=Z||a;if(!aa){h("No service");FailureFn(o("No serviceName specified"));return}if(ac&&ac.length){try{ac=JSON.parse(ac)}catch(ad){h("Invaid input json. Assuming that the service is a special one that expects plain text..")}}else{ac={}}h("Service "+aa+" invoked locally");var Y=s.getResponse(aa,ac);h("local response for service "+aa+" :");h(Y);try{Y=JSON.parse(Y)}catch(ad){h("Response is not a valid JSON. Assumed to be text and returning text instead of JSON.")}if(Y[POCOL.REQUEST_STATUS]==POCOL.STATUS_OK){window[POCOL.LAST_JSON]=Y;X(Y);return}var ab=Y[POCOL.MESSAGES];if(!ab){ab=o("Server indicated failure of service, but offered no explanation!")}Z(ab)};var N=function(Z,X,Y){h("File "+Z.name+" of mime-type "+Z.mime+" of size "+Z.size+" is being uploaded");if(!X){y("No callback funciton. We will set window._uploadedFileKey to the returned key")}var ab=new XMLHttpRequest();ab.onreadystatechange=function(){if(this.readyState!="4"){return}var ac=null;if(ab.status&&ab.status!=200){h("File upload failed with non 200 status : "+ab.status)}else{ac=ab.getResponseHeader(POCOL.FILE_TOKEN)}if(X){X(ac)}else{window._uploadedFileKey=ac}};ab.ontimeout=function(){h("file upload timed out");if(X){X(null)}};if(Y){ab.upload.onprogress=function(ad){if(ad.lengthComputable){var ac=Math.round((ad.loaded*100)/ad.total);Y(ac)}}}try{ab.open(w,H,true);ab.timeout=q;ab.setRequestHeader(POCOL.FILE_NAME,Z.name);h("header field "+POCOL.FILE_NAME+"="+Z.name);if(Z.mime){ab.setRequestHeader(POCOL.MIME_TYPE,Z.mime);h("header field "+POCOL.MIME_TYPE+"="+Z.mime)}ab.send(Z)}catch(aa){y("error during xhr : "+aa);if(X){X(null)}}};var g=function(X){if(!X){y("No file token specified for discard request");return}var Z=new XMLHttpRequest();try{Z.open(w,H,true);Z.setRequestHeader(POCOL.FILE_TOKEN,X);Z.setRequestHeader(POCOL.SERVICE_NAME,POCOL.DISCARD);Z.send()}catch(Y){y("error during xhr for discarding token : "+X+". error :"+Y)}};var I=function(ab,Y,aa,X,Z){X=X||M;if(!ab){y("No file token specified for download request");return}var ad=new XMLHttpRequest();ad.onloadend=function(){var ae=null;if(ad.status&&ad.status!=200){h("non 200 status : "+ad.status);X(null)}else{ae=ad.response}if(X){X(ae,Y,aa)}else{Simplity.message("We successfully downloaded file for key "+ab+" with content-type="+ad.getResponseHeader("Content-Type")+" and total size of "+ad.getResponseHeader("Content-Length"))}};ad.ontimeout=function(){h("file download timed out");X(null)};if(Z){ad.onprogress=function(af){if(af.lengthComputable){var ae=Math.round((af.loaded*100)/af.total);Z(ae)}}}try{ad.open("GET",H+"?"+ab,true);ad.responseType="blob";ad.send()}catch(ac){y("error during xhr for downloading token : "+ab+". error :"+ac)}};var M=function(Z,Y,aa){aa=aa||"text/plain";var X=document.createElement("a");X.download=Y;X.href=window.URL.createObjectURL(Z);X.onclick=function(ac){var ab=this;setTimeout(function(){window.URL.revokeObjectURL(ab.href)},1500)};X.click();X.remove()};var t=function(X){c=X};var D=function(){};var e=function(X){X=X||D;I(POCOL.FILE_NAME_FOR_LOGS,X)};var s=(function(){var ai={tables:{},views:{},responses:{}};var af=function(ao){ai=ao};var Z=function(at,ar,aq){if(!ar){ar={}}if(aq){for(var ap=0;ap<aq.length;ap++){var au=aq[ap];if(at.hasOwnProperty(au)){ar[au]=at[au]}}}else{for(var ao in at){ar[ao]=at[ao]}}return ar};var X=function(aq,ap,ar){if(!ap){ap={}}for(var ao in ar){if(aq.hasOwnProperty(ao)){ap[ao]=aq[ao]}}return ap};var aa=function(ap){var ar=(ap&&ap.length)||0;var ao=[];for(var aq=0;aq<ar;aq++){ao.push(Z(ap[aq]))}return ao};var am=function(ap,au,aq){var ao=[];var av=ap&&ap.length||0;for(var ar=0;ar<av;ar++){var at=ap[ar];if(at[au]==aq){ao.push(at)}}return ao};var Y=function(ar,ao){var aq=[];for(fieldName in ar){if(!ao.hasOwnProperty(fieldName)){continue}var ap={name:fieldName,value:ao[fieldName],op:"="};aq.push(ap);var at=ao[fieldName+"Operator"];if(!at){continue}ap.op=at;if(at!="><"){continue}at=ao[fieldName+"To"];if(!at){throw"we expected a value for "+fieldName+"To because the operator is between (><)"}ap.to=at}return aq};var ac=function(aq,ap){var at=aq[ap.name];switch(ap.op){case"=":return ap.value==at;case"!=":return ap.value!=at;case"<":return at<ap.value;case"<=":return ap.value<=at;case">":return at>ap.value;case">=":return ap.value>=at;case"><":return at>ap.value&&at<ap.to;case"^":return at.toUpperCase().indexOf(ap.value.toUpperCase())==0;case"~":return at.toUpperCase().indexOf(ap.value.toUpperCase())!=-1;case"@":var ar=ap.value&&ap.value.split(",");var au=ar.length||0;for(var ao=0;ao<au;ao++){if(at==ar[ao]){return true}}return false}};var ak=function(av,ao){var ax=[];var ap=av.length||0;var aq=ao.length||0;for(var au=0;au<ap;au++){var aw=av[au];var at=true;for(var ar=0;ar<aq;ar++){if(!ac(aw,ao[ar])){at=false;break}}if(at){ax.push(aw)}}return ax};var ag=function(aq,aw){var ax=aw.length||0;var av=aq.length||0;if(!ax||!av){return aw}for(var at=0;at<ax;at++){var ay=aw[at];for(var ar=0;ar<av;ar++){var ap=aq[ar];var au=ap.baseField;if(!ay.hasOwnProperty(au)){throw"base table does not have the attribute "+au+". Invalid join specification."}var ao=ae(ap.joinedTable,null,ap.joinedField,ay[au],false);if(ao.length>1){throw"We got "+ao.length+" items as children from "+ap.joinedTable+" with its parent key field "+ap.joinedFeild+" = "+ay[ap.fieldName]+" but the join definition has not specified childrenName indicating that we do not expect more than one child items."}if(ao.length){Z(ao[0],ay)}else{h("No row from joining table "+ap.joinedTable+" with  "+ap.joinedField+" = "+ay[au])}}}};var ae=function(aq,ar,ap,av,aw){var aC=ai.tables[aq];if(!aC){throw aq+" is not defined as a table/view under _localData"}var at=aw&&aC.children;h("getting data for table "+aq+" with children = "+at);var aI={};var ax=[];var aG=null;while(true){aG=ai.tables[aq];if(!aG){throw aq+" is not defined as a table/view under _localData"}if(aG.data){break}aI[aq]=true;ax.push(aG);aq=aG.baseTable;if(aI[aq]){throw"We found cyclic dependence of table "+aq}}var aB=aG.data;if(ar){aB=am(aB,aG.key,ar[aG.key])}else{if(ap){aB=am(aB,ap,av)}}aB=aa(aB);if(!aB||!aB.length){return aB}for(var aF=ax.length-1;aF>=0;aF--){ag(ax[aF].joins,aB)}if(!at){return aB}h("going to add rows from children "+at);var aD=aB.length;var au=aC.key;var aA={name:au,op:"="};var ay=[aA];for(var aF=0;aF<at.length;aF++){var az=at[aF];var ao=ae(az,null,null,null,true);for(var aE=0;aE<aD;aE++){var aH=aB[aE];aA.value=aH[au];aH[az]=ak(ao,ay)}}return aB};var aj=function(ao,aq,at){var au=ao.length;for(var ap=0;ap<au;ap++){var ar=ao[ap];if(ar[aq]==at){return ar}}return null};var ab=function(aq,ar){var av=ai.tables[aq];if(!av){throw aq+" is not a table and hence data can not be saved using an express service"}var at=av.key;var aw=ar[at];var ap=av.data;var au=null;if(aw){au=aj(ap,at,aw);if(!au){throw"Item not found for the supplied key. Data can not be saved."}}var ao={};au=X(ar,au,ap[0]);if(!aw){aw=ap[ap.length-1][at];aw++;au[at]=aw;ap.push(au);ao[at]=aw}return ao};var ah=function(at,ax){var aw=ai.tables[at];if(!aw){throw at+" is not a table and hence we are unable to get a list of vname-value pairs"}var au=aw.key;var ar=aw.valueFieldName;if(!au||!ar){throw at+" need to have attributes key and valueFieldName for us to create a list of name-value pairs"}var ay=[];var aq=aw.data;var ao=aq.length;for(var ap=0;ap<ao;ap++){var av=aq[ap];ay.push({key:av[au],value:av[ar]})}return ay};var ad={get:function(ao,ap){var aq=ae(ao,ap,null,null,true);var ar=v();aq=aq&&aq[0];if(aq){Z(aq,ar)}return ar},filter:function(ap,aq){var at=ae(ap,null,null,null,true);if(at.length&&aq){var ar=Y(at[0],aq);if(ar.length){at=ak(at,ar)}}var ao=v();ao[ap]=at;return ao},save:function(ao,ap){return ab(ao,ap)},"delete":function(ao,ap){var aq=ai.tables[ao];if(!aq){throw (ao+" is not a valid table name")}return deleteData(aq,ap)},list:function(ap,aq){var ar=ah(ap,aq);var ao=v();ao[ap]=ar;return ao},suggest:function(ao,ap){return l("local functionaity for suggest is not yet built")}};var an=function(aq,ao){var av=window[POCOL.LOCAL_RESPONSES];av=av&&av[aq];if(av){h("response located in page-specific response");return av}av=window[POCOL.LOCAL_SERVICES];av=av&&av[aq];if(av){h("service function found in page-specific script");if(typeof(av)!=="function"){throw av+" is expected to be a function that returns a valid response to a request for service "+aq}return av(ao)}av=ai.responses[aq];if(av){h("response located in local ready-responses");return av}var at=aq.split("_");if(at.length>1){var ar=at[0];var ap=ad[ar];if(ap){var au=aq.substring(ar.length+1);h("trying "+ar+" for table "+au);return ap(au,ao)}}throw aq+" is not served by the local server."};var al=function(ap,ao){var ar=null;try{ar=an(ap,ao)}catch(aq){ar=l(aq.message?aq.message:aq)}return JSON.stringify(ar)};return{initialize:af,getResponse:al}})();var U=null;if(window.location.protocol==="file:"){var z=sessionStorage[POCOL.LOCAL_STORAGE_NAME];if(z){h("Session storage found in session");U=JSON.parse(z)}else{U=window[POCOL.LOCAL_STORAGE_NAME];if(U){h("local storage found as windows variable..");sessionStorage[POCOL.LOCAL_STORAGE_NAME]=JSON.stringify(U)}}if(U){s.initialize(U);window.addEventListener("beforeunload",function(){sessionStorage[POCOL.LOCAL_STORAGE_NAME]=JSON.stringify(U)})}else{h("local storage NOT found")}V=B}return{log:h,error:y,warn:j,pipeLogging:S,showMessage:C,showMessages:a,overrideShowMessage:m,overrideShowMessages:d,getResponse:V,login:L,logout:r,pushDataToPage:R,uploadFile:N,discardFile:g,downloadFile:I,registerRelogin:t,getLogs:e,htmlEscape:Q,downloadCSV:p()}})();
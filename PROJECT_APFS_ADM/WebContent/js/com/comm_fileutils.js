/**
******************************************************************************************
*** 파일명    : comm_fileutils.js
*** 설명      : 파일관련 공통 유틸 JavaScript
***             $.fileUtil
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2021-02-10              ntarget
******************************************************************************************
**/

$.fileUtil = {
		
	// 파일 포맷 문자열 반환
	formatBytes: function( bytes ) {
		var units = new Array('Bytes', 'KB', 'MB', 'GB');
		var i = 0;
		var c = bytes;
		while (c > 900) {
			c /= 1024;
			i++;
		}
		var s = (Math.round(c*100)/100)+' '+units[i];
		return s;
	},
	
	// 파일 용량체크
	checkMaxbytes: function( fileObj, maxBytes, isAlert ) {
		var bytes = 0;
		// IE용인데 IE8이하는 안됨...
		if (window.ActiveXObject) {
			var fso = new ActiveXObject("Scripting.FileSystemObject");
			var f = fileObj[0].value;
			var o = fso.getFile(f);
			bytes = o.size;
		}
		// IE 외
		else {
			bytes = fileObj[0].files[0].size;
		}
		var s1 = $.fileUtil.formatBytes(maxBytes);
		var s2 = $.fileUtil.formatBytes(bytes);

		if (bytes > maxBytes) {
			if (isAlert) {
				var msg = "첨부파일은 ["+ s1 +"] 이하로 등록가능합니다. (현재크기 : "+s2+")";
				if ($.type(isAlert) === 'function')
					isAlert(msg);
				else
					$.commMsg.alert(msg);
			}
			return false;
		}
		return true;
	},

	// 첨부파일 확장자체크
	checkExtension: function( fileObj, extensions, isAlert ) {
		var fname = fileObj.val();
		var fext  = fname.split(".").pop().toLowerCase();
		if ($.inArray(fext, extensions) == -1) {
			if (isAlert) {
				var msg = "첨부파일은 ["+extensions.join()+"] 파일만 등록 가능합니다.";
				if ($.type(isAlert) === 'function')
					isAlert(msg);
				else
					$.commMsg.alert(msg);
			}
			return false;
		}
		return true;
	},

	// 2022.01.21 첨부파일 파일명길이체크
	checkMaxLength: function( fileName, maxLength, isAlert ) {
		if (!fileName)
			return true;
		if (fileName.length > maxLength) {
			if (isAlert) {
				var msg = [
					'파일명의 최대길이는',
					'['+maxLength+']자를 초과할 수 없습니다.',
					'(현재길이: ['+fileName.length+']자)'
				].join(' ');
				if ($.type(isAlert) === 'function')
					isAlert(msg);
				else
					$.commMsg.alert(msg);
			}
			return false;
		}
		return true;
	},
	
	// 파일명에서 확장자명 추출
	getExtension: function( fileName ) {
	    /** 
	     * lastIndexOf('.') 
	     * 뒤에서부터 '.'의 위치를 찾기위한 함수
	     * 검색 문자의 위치를 반환한다.
	     * 파일 이름에 '.'이 포함되는 경우가 있기 때문에 lastIndexOf() 사용
	     */
	    var _lastDot = fileName.lastIndexOf('.');
		if (_lastDot < fileName.length) {
		    // 확장자 명만 추출한 후 소문자로 변경
		    return fileName.substring(_lastDot+1).toLowerCase();
		}
		return '';
	},

	// BASE64 이미지 파일 다운로드
	downloadBase64Image: function( base64Image, fileName ) {
		if (window.navigator && 
			window.navigator.msSaveOrOpenBlob) {
			// 실제 데이터는 iVBO...부터이므로 split한다.
			var imgData = atob(base64Image.split(',')[1]);
			var len  = imgData.length;
			var buf  = new ArrayBuffer(len); // 비트를 담을 버퍼를 만든다.
			var view = new Uint8Array(buf); // 버퍼를 8bit Unsigned Int로 담는다.
			var blob, i;
			for (i = 0; i < len; i++) {
			  view[i] = imgData.charCodeAt(i) & 0xff; // 비트 마스킹을 통해 msb를 보호한다.
			}
			// Blob 객체를 image/png 타입으로 생성한다. (application/octet-stream도 가능)
			blob = new Blob([view], { type: 'image/png' });
		    window.navigator.msSaveOrOpenBlob(blob, fileName);
		    return;
		}
		var download      = document.createElement('a');
		download.href     = base64Image;
		download.target   = '_blank';
		download.download = fileName;
		var evt = document.createEvent('MouseEvents');
		evt.initMouseEvent('click', true, true, window, 1, 0, 0, 0, 0, false, false, false, false, 0, null);
		download.dispatchEvent(evt);
	},

	// 캔버스 이미지 생성
	createCanvasImage: function( element, fileName ) {
        var dataURL = element.toDataURL('image/png');
        dataURL = dataURL.replace(/^data:image\/[^;]*/, 'data:application/octet-stream');
        dataURL = dataURL.replace(/^data:application\/octet-stream/, 'data:application/octet-stream;headers=Content-Disposition%3A%20attachment%3B%20filename='+fileName);
		return dataURL;
	},

	// 2021.12.30 미리보기 가능여부 반환
	enablePreview: function( type ) {
		return ($.inArray(type, ['PDF','HWP','IMG','TXT']) >= 0);
	},
	// 2021.08.17 미리보기가 가능한지 확인
	getPreviewType: function( fileName ) {
		// 확장자 추출
		let ext = $.fileUtil.getExtension( fileName );
		//if      (isIE())
		//	return 'NON';
		if      ($.inArray(ext, ['pdf']) >= 0)
			return 'PDF';
		else if ($.inArray(ext, ['hwp']) >= 0)
			return 'HWP';
		else if ($.inArray(ext, ['jpg','gif','png']) >= 0)
			return 'IMG';
		else if ($.inArray(ext, ['txt']) >= 0)
			return 'TXT';
		else
			return 'NON';
	},
	// 첨부파일 다운로드
	// args.params : 조건
	// args.url    : 다운로드 URL
	// args.log    : 이력등록설정
	// args.log.params : 이력등록조건 (필수값: atchFileSn, progUrl)
	download: function( args ) {
		if (args.log) {
			popup.openDownLog({
				params: args.log.params,
				saveCallback: function() {
					// 파일 다운로드 실행
					$.formUtil.submitForm(args.url, {params: args.params});
				}
			});
		}
		else {
			// 파일 다운로드 실행
			$.formUtil.submitForm(args.url, {params: args.params});
		}
		return false;
	},
	// 2023.01.09 LSH 첨부파일 다운로드 및 진행상태 표시
	// args.params : 조건
	// args.url    : 다운로드 URL
	// args.progress.start : 시작시 진행상태 표시함수
	// args.progress.ing   : 진행중 진행상태 표시함수
	// args.progress.end   : 종료시 표시함수
	// [사용예제]
	// $.fileUtil.downloadProgress( $.extend(args, {
	// 		progress: {
	//	 		start: function(url) {
	//	 			$('#footer').html(url);
	//	 		},
	//	 		ing: function(pr) {
	//	 			$('#footer').html('loaded='+pr.loaded+',total='+pr.total);
	//	 		},
	//	 		end: function(fileName) {
	//	 			$('#footer').html(fileName);
	//	 		}
	// 		}
	// }));
	// 
	downloadProgress: function( args ) {
		
		let url = args.url + '?' + $.param(args.params);

		if (args.progress &&
			args.progress.start)
			args.progress.start(url);

	    let blob;
		let fileName = '';
	    let xmlHTTP = new XMLHttpRequest();
	    xmlHTTP.open('GET', url, true);
	    xmlHTTP.responseType = 'arraybuffer';
	    xmlHTTP.onreadystatechange = function() {
			if (xmlHTTP.readyState == XMLHttpRequest.DONE) {
				if (xmlHTTP.status == 200) {
					let disposition = xmlHTTP.getResponseHeader("Content-Disposition");
					if (disposition && 
						disposition.indexOf("attachment") !== -1) {
					    let filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
					    let matches = filenameRegex.exec(disposition);
					    if (matches != null && matches[1]) {
					        fileName = decodeURI(matches[1].replace(/['"]/g, ""));
					    }
					}
				}
				//ERROR
				else {
					var enc = new TextDecoder("utf-8");
					var arr = new Uint8Array(xmlHTTP.response);
					var txt = JSON.parse(enc.decode(arr));
					$.commMsg.alert(txt.message);						
				}
			}
	    };
	    xmlHTTP.onload = function(e) {
	        blob = new Blob([this.response]);
	    };
	    xmlHTTP.onprogress = function(pr) {
	        //pr.loaded - current state
	        //pr.total  - max
			if (args.progress &&
				args.progress.ing)
				args.progress.ing(pr);
	    };
	    xmlHTTP.onloadend = function(e){

			if (args.progress &&
				args.progress.end)
				args.progress.end(fileName);

			if (xmlHTTP.status == 200) {
				// for IE
        		if (window.navigator && window.navigator.msSaveOrOpenBlob) {
            		window.navigator.msSaveOrOpenBlob(blob, fileName);
        		} 
				else {
            		var URL         = window.URL || window.webkitURL;
            		var downloadUrl = URL.createObjectURL(blob);
            		if (fileName) {
                		var a = document.createElement("a");
	                	// for safari
	                	if (a.download === undefined) {
	                    	window.location.href = downloadUrl;
	                	} 
						else {
	                    	document.body.appendChild(a);
							a.style    = "display: none";
	                    	a.href     = downloadUrl;
	                    	a.download = fileName;
	                    	a.click();
							URL.revokeObjectURL(downloadUrl);
	                	}
	            	} 
					else {
	                	window.location.href = downloadUrl;
	            	}
        		}
			}
	    }
	    xmlHTTP.send();
	},
	
	
	
	// 첨부파일 미리보기
	// args.type : 미리보기타입 (HWP / PDF / IMG / TXT)
	// args.url  : 파일URL
	preview: function( args ) {
		
		if (!$.fileUtil.enablePreview(args.type))
			return false;
		
		let openArgs = false;
		if (args.type == 'PDF') {
			// PDF VIEWER OPTIONS
			let pdfOptions = {
				pdfOpenParams: {
					navpanes: 0,
					toolbar: 0,
					statusbar: 0,
					view: "FitV",
					page: 1
				},
				width : "550px",
				height: "400px",
				forcePDFJS: true
			};
			if (isIE())
				pdfOptions['PDFJS_URL'] = getUrl("/plugins/pdfjs-2.12.13/es5/web/viewer.html");
			else
			 	pdfOptions['PDFJS_URL'] = getUrl("/plugins/pdfjs-2.12.13/viewer.html");
			openArgs = {
				size:    BootstrapDialog.SIZE_WIDE,
				title:   '파일 미리보기',
				message: '<div id="filePreview" style="height:800px;"></div>',
				func: function() {
					PDFObject.embed(args.url, "#filePreview", pdfOptions);
				}
			};
		}
		else if (args.type == 'HWP') {
			let url = getUrl("/plugins/hwpjs-0.0.3/viewer.html?file=")+ args.url;
			openArgs = {
				size:    BootstrapDialog.SIZE_WIDE,
				title:   '파일 미리보기',
				message: '<iframe id="filePreview" style="width:100%;height:800px;" src="'+url+'"></iframe>'
			};
		}
		else if (args.type == 'IMG') {
			openArgs = {
				size:    BootstrapDialog.SIZE_WIDE,
				title:   '파일 미리보기',
				message: '<img id="filePreview" src="'+args.url+'" style="width:100%;height:100%;object-fit:contain;" />',
				cssClass: 'app-c'
			};
		}
		else if (args.type == 'TXT') {
			openArgs = {
				size:    BootstrapDialog.SIZE_WIDE,
				title:   '파일 미리보기',
				message: '<iframe id="filePreview" style="width:100%;height:800px;" class="app-frame" src="'+args.url+'"></iframe>'
			};
		}
		else {
			return false;
		}
		if (openArgs) {
			//2023.08.14 LSH 사용안함 (다른팝업으로 대체할것)
			//$.commModal.openView(openArgs);
		}
		return false;
	},
	// 2022.01.07 LSH 파일명을 일정사이즈로 줄이기 (확장자는 유지)
	getMaskName: function( fileName, nameLen ) {
		if (!nameLen)
			return fileName;
		var _fileExt = '';
		var _filePre = '';
	    var _lastDot = fileName.lastIndexOf('.');
		if (_lastDot < fileName.length) {
		    _filePre = fileName.substring(0,_lastDot);
			_fileExt = fileName.substring(_lastDot+1);
		}
		else {
		    _filePre = fileName;
		}
		if (_filePre.length > nameLen) {
			_filePre = _filePre.substring(0,nameLen)+'...';
		}
		return _filePre+'.'+_fileExt;
	}
};

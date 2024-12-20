"use strict";

// jQuery FIle Upload Demo
// =============================================================
$(function () {
  // Change this to the location of your server-side upload handler:
//  var url = '//jquery-file-upload.appspot.com/'; // const url = (window.location.hostname === 'blueimp.github.io') ? '//jquery-file-upload.appspot.com/' : 'server/php/'
   // var url = '/fileUploadPost'
  // file upload avatar
  // =============================================================

 

  $('#fileupload-customInput, #fileupload-btn, #fileupload-dropzone').fileupload({
  //  url: url,
    dropZone: null,
  //  dataType: 'json',
    autoUpload: true,
  //  acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
 //   maxFileSize: 999000,
    // Enable image resizing, except for Android and Opera,
    // which actually support image resizing, but fail to
    // send Blob objects via XHR requests:
    disableImageResize: /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
    singleFileUploads: 0,
    previewMaxWidth: 32,
    previewMaxHeight: 32,
    previewCrop: true
  }).on('fileuploadadd', function (e, data) {

    $.each(data.files, function (index, file) {
	//alert('fileuploadadd');

    });
    
  }).on('fileuploadprocessalways', function (e, data) {
    var index = data.index;
    var file = data.files[index];
    var node = $(data.context.children()[index]);






    if (index + 1 === data.files.length) {

    }

    if (file.error) {

    }
  }).on('fileuploadprogressall', function (e, data) {
	//alert('fileuploadprogressall');

  }).on('fileuploaddone', function (e, data) {
    $.each(data.result.files, function (index, file) {
	//alert('fileuploaddone');
      
    });
  }).on('fileuploadfail', function (e, data) {
    $.each(data.files, function (index) {
     // alert( 'fileuploadfail');
    });
  }).prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled'); // upload dropzone
  // =============================================================

  var dropZone = $('#dropzone');
  $('#fileupload-dropzone').fileupload('option', 'dropZone', dropZone);
  dropZone.on('dragover', function () {
    dropZone.addClass('hover');
  }).on('drop dragleave', function () {
    dropZone.removeClass('hover');
  });
});
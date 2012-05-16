//<![CDATA[
function uncheckOtherBoxes(chkBox) {
	var id = chkBox.name.substring(chkBox.name.lastIndexOf(':'));
	var emailPanel = $(chkBox).parent().parent().parent().parent().parent()
			.parent().parent();
	var checkboxes = $(emailPanel).find(':checkbox');

	for ( var i = 0; i < checkboxes.length; i++) {
		checkboxes[i].checked = false;
	}
	chkBox.checked = true;
}

function editLastDatatableRow() {

	jQuery('.ui-datatable-data tr').last().find('span.ui-icon-pencil').each(
			function() {
				jQuery(this).click()
			});
}

// ]]>

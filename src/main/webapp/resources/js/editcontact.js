#-------------------------------------------------------------------------------
# Copyright 2012 Christian Ternes and Thorsten Volland
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#-------------------------------------------------------------------------------
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

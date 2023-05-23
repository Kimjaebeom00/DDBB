let projectInfoInit = function() {
    let projectId = $('#currentProjectId').val();
    let target = $('#projectForm-'+projectId);

    target.addClass('selected');

    let comparedCodeInfoMap = api.compareCode(projectId);
    // console.log(comparedCodeInfoMap);
    const $beforeCodeContainer = $('.before_code');
    const $currentCodeContainer = $('.current_code');

    comparedCodeInfoMap.beforeList.forEach((beforeLine, index) => {
        if(comparedCodeInfoMap.codeMap.before.modify !== undefined && comparedCodeInfoMap.codeMap.before.modify.indexOf(index) >= 0) {
            $beforeCodeContainer.append('<p class="modified_code">' + beforeLine + '</p>');
        } else if(comparedCodeInfoMap.codeMap.before.delete !== undefined && comparedCodeInfoMap.codeMap.before.delete.indexOf(index) >= 0) {
            $beforeCodeContainer.append('<p class="deleted_code">' + beforeLine + '</p>');
        } else if(beforeLine === "\r") {
            $beforeCodeContainer.append('<p>&nbsp;</p>');
        } else {
            $beforeCodeContainer.append('<p>' + beforeLine + '</p>');
        }
    });
    comparedCodeInfoMap.currentList.forEach((currentLine, index) => {
        if(comparedCodeInfoMap.codeMap.current.add !== undefined && comparedCodeInfoMap.codeMap.current.add.indexOf(index) >= 0) {
            $currentCodeContainer.append('<p class="added_code">' + currentLine + '</p>');
        } else if(comparedCodeInfoMap.codeMap.current.modify !== undefined && comparedCodeInfoMap.codeMap.current.modify.indexOf(index) >= 0) {
            $currentCodeContainer.append('<p class="modified_code">' + currentLine + '</p>');
        } else if(currentLine === "\r") {
            $currentCodeContainer.append('<p>&nbsp;</p>');
        } else {
            $currentCodeContainer.append('<p>' + currentLine + '</p>');
        }
    });

    let beforeIndex = 0;
    let interval = 0;
    let tempInterval = 0;
    let intervaled = 0;
    if(comparedCodeInfoMap.codeMap.before.add !== undefined) {
        comparedCodeInfoMap.codeMap.before.add.forEach((index) => {
            if(beforeIndex === 0)
                beforeIndex = index;
            else {
                if(beforeIndex + 1 === index)
                    tempInterval++;
                else {
                    intervaled++;
                    interval = tempInterval + intervaled;
                }
                beforeIndex = index;
            }
            $('.before_code p:nth-child(' + (index + interval) + ')').after('<p class="added_code">&nbsp;</p>');
        });
    }

    if(comparedCodeInfoMap.codeMap.current.delete !== undefined) {
        beforeIndex = 0;
        interval = 0;
        tempInterval = 0;
        intervaled = 0;
        comparedCodeInfoMap.codeMap.current.delete.forEach((index) => {
            if(beforeIndex === 0)
                beforeIndex = index;
            else {
                if(beforeIndex + 1 === index)
                    tempInterval++;
                else {
                    intervaled++;
                    interval = tempInterval + intervaled;
                }
                beforeIndex = index;
            }
            $('.current_code p:nth-child(' + (index + interval) + ')').after('<p class="deleted_code">&nbsp;</p>');
        });
    }
}

const goToInsertCode = function() {
    const projectId = $('#currentProjectId').val();
    const form = $('#InsertCodeForm');

    $('#projectIdForCode').val(projectId);

    form.submit();
}
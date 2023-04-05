var api = {
    projectMembers: function(projectId) {
        var params = {
            projectId: projectId
        };

        $.ajax({
            type: "POST",
            url: "/projectMember/list",
            data: params,
            success: function(result){
                console.log(result.code);
                console.log(result);
            },
            error: function(error){
                alert("API 에러가 발생했습니다.");
                console.error(error);
            }
        });
    }
}
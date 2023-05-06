var api = {
    projectMembers: function(projectId) {//컨트롤러 메소드 이름이랑 같다 ->오브젝트 형식
        var params = {
            projectId: projectId//왼쪽은 키값 오른쪽은 벨류
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
    },
    findId: function(email,name) {//컨트롤러 메소드 이름이랑 같다 ->오브젝트 형식
        let params = {
            email: email,//왼쪽은 키값 오른쪽은 벨류
            name: name
        };

        $.ajax({
            type: "POST",
            url: "/signFindId",
            data: params,
            success: function(result){
                let msg;
                if(result!=""){
                    msg = "ID는: "+result+"입니다.";
                }else{
                    msg = "조회되는 정보가 없습니다."
                }
                alert(msg);
            },
            error: function(error){
                alert("API 에러가 발생했습니다.");
                console.error(error);
            }
        });
    },
    getId: function(id) {//컨트롤러 메소드 이름이랑 같다 ->오브젝트 형식
        let params = {
           id : id
        };

        $.ajax({
            type: "POST",
            url: "/idValidation",
            data: params,
            success: function(result){
                let msg;
                if(result == false){
                    msg = "이 ID는 사용가능 합니다.";
                }else if(result == true){
                    msg = "이 ID는 사용이 불가능합니다.";
                }
                alert(msg);
            },
            error: function(error){
                alert("API 에러가 발생했습니다.");
                console.error(error);
            }
        });
    },
    getEmail: function(email) {//컨트롤러 메소드 이름이랑 같다 ->오브젝트 형식
        let params = {
            email : email
        };

        $.ajax({
            type: "POST",
            url: "/emailValidation",
            data: params,
            success: function(result){
                let msg;
                if(result == false){
                    msg = "이 이메일은 사용가능 합니다.";
                }else if(result == true){
                    msg = "이 이메일은 사용이 불가능합니다.";
                }
                alert(msg);
            },
            error: function(error){
                alert("API 에러가 발생했습니다.");
                console.error(error);
            }
        });
    }

}
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
                console.log(error);
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
                console.log(error)
                alert("API 에러가 발생했습니다.");
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
    },

    getPassword: function(id,nickname,email) {//컨트롤러 메소드 이름이랑 같다 ->오브젝트 형식
        let params = {
            id : id,
            nickname : nickname,
            email : email
        };

        $.ajax({
            type: "POST",
            url: "/signFindPassword",
            data: params,
            success: function(result){
                let msg;
                if(result == true){
                    msg = "임시 비밀번호가 전송되었습니다.";
                }else if(result == false){
                    msg = "입력한 값이 틀립니다.";
                }else{
                    msg = "입력된 값이 없습니다.";
                }
                alert(msg);
            },
            error: function(error){
                alert("API 에러가 발생했습니다.");
                console.error(error);
            }
        });
    },

    compareCode: function(projectId) {
        let comparedCodeInfo;
        const params = {
            projectId : projectId
        };

        $.ajax({
            async: false,
            type: "POST",
            url: "/codeCompare",
            data: params,
            success: function(result){
                comparedCodeInfo = result;
            },
            error: function(error){
                alert("API 에러가 발생했습니다.");
                console.error(error);
            }
        });

        return comparedCodeInfo;
    }


}
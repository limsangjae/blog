let token = document.querySelector("meta[name = '_csrf']").getAttribute("content");

const findForm = document.querySelector('#findForm');

let progress = document.querySelector('#progress');
let findContent = document.querySelector('#find-content');

progress.hidden = true;

findForm.addEventListener("submit", (e) => {

    e.preventDefault();

    let loginId = document.querySelector('#loginId').value;
    let email = document.querySelector('#email').value;

    if(loginId === null || loginId.length === 0){
        alert("아이디를 입력하여 주시기 바랍니다.");
        return;
    }

    if(email === null || email.length === 0){
        alert("이메일을 입력하여 주시기 바랍니다.");
        return;
    }

    let data = {
        method: "POST",
        body: JSON.stringify(
            {
                loginId : loginId,
                email : email,
            }
        ),
        headers:{
            'Content-Type' : 'application/json',
            'X-CSRF-TOKEN': token
        }
    }

    findContent.style.display = 'none';
    progress.hidden = false;

    fetch("http://localhost:8085/mails/find/pw",data)
        .then(
            (response) =>{
                return response;
            }
        )
        .then(
            (data) => {
                if(!data){
                findContent.hidden = false;
                progress.hidden = true;
                    alert("이메일 발송 실패, 이메일을 확인하여 주시기 바랍니다.");
                    return;
                }else{
                    alert("발급된 임시 비밀번호를 입력하신 이메일로 보냈습니다.");

                    window.location.replace("http://localhost:8085/");
                }
            }
        )
        .catch(
            (error) => {
                findContent.hidden = false;
                progress.hidden = true;
                alert("메일 발송에 실패하였습니다. 이메일 혹은 아이디를 확인해 주시기 바랍니다.")
            }
        )


})
const nicknameInput = document.getElementById("nickname-input");
const setNicknameButton = document.getElementById("set-nickname-btn");
const nicknameMsg = document.getElementById("nickname-msg");

if(nicknameInput) {
    nicknameInput.addEventListener('keyup', function () {
        nicknameMsg.style.display = "none";
    });
}

if (setNicknameButton) {
    setNicknameButton.addEventListener('click', e =>{
        nicknameInput.value = nicknameInput.value.trim();

        fetch("/api/checkNickname", {
            method: "POST",
            headers: {
                "Content-Type": "text/plain"
            },
            body: nicknameInput.value
        })
            .then(response => response.text())
            .then(result => {
                const isAvailable = result === "true";

                if (isAvailable) {
                    nicknameMsg.style.display = "none";

                    let userId = document.getElementById("user-id").value;
                    fetch(`/api/user/${userId}`,{
                        method: 'PUT',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify({
                            nickname: nicknameInput.value
                        })
                    }).then(() => {
                        alert("설정 되었습니다");
                        location.replace('/home');
                    })
                } else {
                    nicknameMsg.style.display = "inline";
                }
            })
    })
}
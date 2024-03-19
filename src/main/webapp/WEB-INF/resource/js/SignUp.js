const login = document.querySelector('.login');	
const inputArea = document.querySelectorAll('.input-area');
const label = document.querySelectorAll('.input-field label');
const p = document.querySelectorAll('.input-field p');



inputArea.forEach((input, i) => {
	input.addEventListener('input', () => {
		if(input.value === ""){
			input.classList.add('danger');
            label[i].classList.add('danger');
            p[i].innerText = "Required";
            p[i].classList.add('required');
		}else{
			 input.classList.remove('danger');
			 label[i].classList.remove('danger');
			 if(input.id === "email"){
				const ar = input.value.split('');
					if(ar.includes("@") && ar.includes(".")){
						const index = ar.indexOf("@");
						if(ar.indexOf("@") >=3 && ar.indexOf(".") >= index+3 ){
							p[i].innerText = "";
			   				 p[i].classList.remove('required');
						}else{
							 p[i].innerText = "Invalid email address";
			   				 p[i].classList.add('required');
						}
					}else{
						 p[i].innerText = "Invalid email address";
		   				 p[i].classList.add('required');
					}
	
			 }else if(input.id ==="password"){
				 const ar = input.value.split('');
				 let uppercase = false;
				  let lowercase = false;
				  let digit = false;
				  let symbol = false;
				  for (let i = 0; i < ar.length; i++) {
					  if (ar[i] >= 'A' && ar[i] <= 'Z') {
					    uppercase = true;
					  } else if (ar[i] >= 'a' && ar[i] <= 'z') {
					    lowercase = true;
					  } else if (ar[i] >= '0' && ar[i] <= '9') {
					    digit = true;
					  } else {
					    symbol = true;
					  }
					}
				  if(ar.length >= 6 && ar.length <= 13){
				 if(uppercase && lowercase && digit && symbol){
					 p[i].innerText = "";
					 p[i].classList.remove('required');
				 }else{
					 p[i].innerText = "It must contain at least one lowercase letter (a-z), 1 uppercase letter (A-Z), 1 numeric digit (0-9), and 1  special characters (@, $)";
					 p[i].classList.add('required');
				 }
				  }else{
					  p[i].innerText = "Password must contain: minimum 6 and maximum 13 characters";
						 p[i].classList.add('required');
				  }
				
			 }else{
			  p[i].innerText = "";
			 p[i].classList.remove('required');
			 }
		}
	})
	
	login.addEventListener('click', () => {
		if(input.value === ""){
			label[i].classList.add('danger');
			p[i].innerText ="Required";
			p[i].classList.add('required');
		}else if(p[0].innerText === "" && p[1].innerText === "" && p[2].innerText === "" && p[3].innerText === ""){
			login.type = "submit";
		}
	
})
	
})

	document.addEventListener("DOMContentLoaded", () => {
   	 const mainDiv = document.querySelector('.inner-box');
   	 mainDiv.style.opacity = '1';
   	 mainDiv.style.transform = 'scale(1)';
	});
	

		
				
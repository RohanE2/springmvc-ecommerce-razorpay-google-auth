		const nav = document.querySelector('.main-nav');
			let scroll = 0;
			window.addEventListener('scroll',() =>{
				console.log(window.scrollY);
				if(window.scrollY > scroll){
					nav.classList.add('scrolled');
				}else{
					nav.classList.remove('scrolled');
				}
			scroll = window.scrollY;
			})


	const continueBtn = document.querySelector('.continue-btn');	
	const inputArea = document.querySelectorAll('.input-area');
	const label = document.querySelectorAll('.input-field label');
	const p = document.querySelectorAll('.input-field p');
	const greenbtn = document.querySelectorAll('.green-btn');
	const checkbox = document.querySelector('.checkbox input');


	inputArea.forEach((input, i) => {
		input.addEventListener('input', () => {
			if(input.value === ""){
				input.classList.add('danger');
	            label[i].classList.add('danger');
	            p[i].innerText = "Required";
	            p[i].classList.add('required');
	            greenbtn[i].style.display = "none";
			}else{
				 input.classList.remove('danger');
				 label[i].classList.remove('danger');
				 p[i].innerText = "";
				 p[i].classList.remove('required');
				 greenbtn[i].style.display = "block";
				 if(input.id === "email"){
					const ar = input.value.split('');
						if(ar.includes("@") && ar.includes(".")){
							const index = ar.indexOf("@");
							if(ar.indexOf("@") >=3 && ar.indexOf(".") >= index+3 ){
								p[i].innerText = "";
				   				 p[i].classList.remove('required');
				   				greenbtn[i].style.display = "block";
							}else{
								 p[i].innerText = "Invalid email address";
				   				 p[i].classList.add('required');
				   				greenbtn[i].style.display = "none";
							}
						}else{
							 p[i].innerText = "Invalid email address";
			   				 p[i].classList.add('required');
			   				greenbtn[i].style.display = "none";
						}
		
				 }else if(input.id ==="phone"){
					if(input.value.length !== 10){
						 p[i].innerText = "Invalid Phone Number";
		   				 p[i].classList.add('required');
		   				greenbtn[i].style.display = "none";
					}
					
				 }else{
					 
				 }
			}
		})
	
		
			continueBtn.addEventListener('click', () => {
		if(input.value === ""){
			label[i].classList.add('danger');
			p[i].innerText ="Required";
			p[i].classList.add('required');
		}else {
	        let flag = true;
	        for (let j = 0; j < p.length; j++) {
	            if (p[j].innerText !== "") {
	                flag = false;
	            }
	        }
	        if (flag) {
	        	if(checkbox.checked){
	        		continueBtn.type = "submit";
	        		
	        	}
	            
	        }
	    }
		
		})
	})
const currentDate = new Date();
currentDate.setDate(currentDate.getDate() + 7);

const lastDate = new Date();
lastDate.setDate(currentDate.getDate() + 20);

const fromDate = currentDate.toLocaleDateString('en-US', { weekday: 'short', day: 'numeric', month: 'short' });
const toDate = lastDate.toLocaleDateString('en-US', { weekday: 'short', day: 'numeric', month: 'short' });

const arrivingDate = document.querySelector('.date-text');
arrivingDate.textContent = "Arrives " + fromDate + " - " + toDate;


const amountInput = document.querySelector('.amount');
	const amount = amountInput.value;
	console.log(amount);
	const orderId = document.querySelector('.orderId');
	console.log(orderId);
	var options = {
		    "key": "rzp_test_hIzNEXTrDELfJo", // Enter the Key ID generated from the Dashboard
		    "amount": amount*100, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
		    "currency": "INR",
		    "name": "MVC Project",
		    "description": "Test Transaction",
		    "image": "https://example.com/your_logo",
		    "order_id": orderId.value, //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
		    "handler": function (response){
//		        alert(response.razorpay_payment_id);
//		        alert(response.razorpay_order_id);
//		        alert(response.razorpay_signature)
		 
		        var paymentId = response.razorpay_payment_id;

		       window.location.href = 'http://localhost:8080/mvcproject1/paymentCallback?payment_id='+ paymentId + '&order_id='+ orderId.value ;
		        
		    },
		    "prefill": {
		        "name": "",
		        "email": "",
		        "contact": ""
		    },
		    "notes": {
		        "address": "Razorpay Corporate Office"
		    },
		    "theme": {
		        "color": "#3399cc"
		    }
		};
		var rzp1 = new Razorpay(options);
		rzp1.on('payment.failed', function (response){
//		        alert(response.error.code);
//		        alert(response.error.description);
//		        alert(response.error.source);
//		        alert(response.error.step);
//		        alert(response.error.reason);
//		        alert(response.error.metadata.order_id);
//		        alert(response.error.metadata.payment_id);
		});
		if (orderId.value !== "null") {
			    rzp1.open();
			}

		
		const navbar = document.querySelector('.nav-toggle');
		const navLink = document.querySelector('.nav-links');
		
		navbar.addEventListener('click',() =>{
			navLink.classList.toggle('nav-links-active-top');
			
		});

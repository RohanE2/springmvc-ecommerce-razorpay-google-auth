	const accordians = document.querySelectorAll('.accordian');
	
		accordians.forEach(accordian =>{
			const icon = accordian.querySelector('.icon');
			const content = accordian.querySelector('.acc-content');


			
			accordian.addEventListener('click',() =>{
				if(icon.classList.contains('active')){
					icon.classList.remove('active');
					content.style.maxHeight = null;
				}else{
					icon.classList.add('active');
					content.style.maxHeight = content.scrollHeight + 'px';
				}
			})	
		})
		
	const controls = document.querySelectorAll('.controls .box1');
	const images = document.querySelectorAll('.product-img-card img');
	const mainCard = document.querySelector('.main-img-card-img img');
	const imgIndex = document.querySelector('.imgIndex');
	let index = imgIndex.textContent;
	
	
	controls.forEach((btn)=>{
		
	btn.addEventListener('click', () => {
		if(btn.id === 'right'){
	    index++;
	    if (index === images.length) {
	        index = 0;
	    }
		}else{
			  index--;
			    if (index < 0) {
			        index = images.length - 1;
			    }
		}
		 mainCard.src = images[index].src;
		 images.forEach((img) =>{
				img.classList.remove('active-img-card');
			})
		 images[index].classList.add('active-img-card');
	});
	});

	const imagesContainer = document.querySelectorAll('.product-img-card ');
	const images1 = document.querySelectorAll('.product-img-card img');
	const displayImg  = document.querySelector('.main-img-card img');
	
	
	imagesContainer.forEach((con,i) =>{
		const img = con.querySelector('img');	
		con.addEventListener('mouseenter',() =>{
			displayImg.src = img.src;
			index = i;
			images1.forEach((img) =>{
				img.classList.remove('active-img-card');
			})
			
			img.classList.add('active-img-card');
			})
		
		
	})		
	const displayImg1  = document.querySelector('.main-img-card img');
	const imgColors = document.querySelectorAll('.product-content-img-color');
	const imgColorImg = document.querySelectorAll('.product-content-img-color img');
	const colorInput = document.querySelectorAll('.product-content-img-color input');
	const colorImg  = document.querySelectorAll(' .colorChoice');
	const getSizeValue1 = document.querySelectorAll('.getSizeValue ');
	const wishlistIcon = document.querySelector('.checkout-btn i  ');
	
	const wishProd = document.querySelector('.wishProd');
	const wishprodVal = wishProd.value.split('&');
	const wishproductValue = [];
	
	wishprodVal.forEach((val)=>{
		const splitValues = val.split('$');
		wishproductValue.push(splitValues);
	})

	wishproductValue.forEach((prod)=>{
		if(prod[0] === colorImg[0].value && prod[1] === getSizeValue1[0].value){
			wishlistIcon.classList.add('fa-solid');
		}
	})
	
	imgColors.forEach((color,e)=>{
		if(colorInput[e].value === colorImg.value){
			color.classList.add('selectedColor');
		}
		color.addEventListener('click',() =>{
			displayImg1.src = imgColorImg[e].src;
			colorImg[0].value = colorInput[e].value;
			colorImg[1].value = colorInput[e].value;
		
				wishlistIcon.classList.remove('fa-solid');
				wishlistIcon.classList.add('fa-regular');
		
			wishproductValue.forEach((prod)=>{
				console.log(prod[0]);
				console.log(colorImg[0]);
				if(prod[0] === colorImg[0].value && prod[1] === getSizeValue1[0].value){
					wishlistIcon.classList.remove('fa-regular');
					wishlistIcon.classList.add('fa-solid');
					console.log('rohan');
				}
			})
			
			imgColors.forEach((img)=>{
				img.classList.remove('selectedColor');
			})
			color.classList.add('selectedColor');
			
		})
	})
	 const sizebtn = document.querySelectorAll('.size-btn .sizeNot ');
	 const getSizeValue = document.querySelectorAll('.getSizeValue ');
	 const getSizeAlert = document.querySelector('.size-btn ');
	 const size = document.querySelector('.size p');
	 const sizeMsg = document.querySelector('#sizeMsg');
	console.log(size);
	 sizebtn.forEach((sbtn)=>{
		 sbtn.addEventListener('click',() =>{
			 if(getSizeAlert.classList.contains('getSize')){
				 getSizeAlert.classList.remove('getSize');
				 size.classList.remove('selectSize');
				 sizeMsg.innerText = '';
			 }
			 getSizeValue[0].value = sbtn.value;
			 getSizeValue[1].value = sbtn.value;
			 
				wishlistIcon.classList.remove('fa-solid');
				wishlistIcon.classList.add('fa-regular');
		
			wishproductValue.forEach((prod)=>{
				if(prod[0] === colorImg[0].value && prod[1] === getSizeValue1[0].value){
					wishlistIcon.classList.remove('fa-regular');
					wishlistIcon.classList.add('fa-solid');
					console.log('rohan');
				}
			})
			
			 sizebtn.forEach(btn => {
				  btn.classList.remove('sizeSelected');
				});
			 sbtn.classList.add('sizeSelected');
		 })
	 })

	const buttons = document.querySelectorAll('.arrow i');
	const productContainer = document.querySelector('.more-products-content');
	const imgCard = document.querySelector('.more-products');
	const width = document.querySelector('.more-products-content').offsetWidth;
	console.log(buttons);
	buttons.forEach(button =>{
		button.addEventListener('click',() =>{
			if(button.id ==="left"){
				console.log(button.id);
				
				imgCard.scrollLeft += -width -10;
				
			
			}else{
				console.log(button.id);
				imgCard.scrollLeft += width + 10;
			}
		});
		
	});
		
	
	const body = document.querySelector('.overlay');
	const popup = document.querySelector('.popup');
	const close = document.querySelector('.popup-top i');
	if(popup !=null){
		
		body.style.opacity = '1';
		body.style.visibility = 'visible';
		setTimeout(() => {
			popup.classList.add('popup-animation');
		}, 300);
		close.addEventListener('click',() =>{
			body.style.opacity = '0';
			body.style.visibility = 'hidden';	
			popup.classList.remove('popup-animation');
		});
		body.addEventListener('click',() =>{
			body.style.opacity = '0';
			body.style.visibility = 'hidden';	
			popup.classList.remove('popup-animation');
		});
		
	}
		 
		
		const body1 = document.querySelector('.overlay');
		const popup1 = document.querySelector('.popup1');
		const close1 = document.querySelector('.popup-top1 i');
		
			if(popup1 !=null){
				
				body1.style.opacity = '1';
				body1.style.visibility = 'visible';
				setTimeout(() => {
					popup1.classList.add('popup-animation');
				}, 300);
				close1.addEventListener('click',() =>{
					body1.style.opacity = '0';
					body1.style.visibility = 'hidden';	
					popup1.classList.remove('popup-animation');
				});
				body1.addEventListener('click',() =>{
					body1.style.opacity = '0';
					body1.style.visibility = 'hidden';	
					popup1.classList.remove('popup-animation');
				});
				setTimeout(function(){
					body1.style.opacity = '0';
					body1.style.visibility = 'hidden';	
					popup1.classList.remove('popup-animation');										
				},5000);
				
			};
			
			const body2 = document.querySelector('.body');
			const loginPopup = document.querySelector('.login-popup');
			const loginClose = document.querySelector('.info i');
				if(loginPopup !=null){
					body2.style.opacity = '1';
					body2.style.visibility = 'visible';
				 	loginPopup.classList.add('details-zoom-in');
				 	loginPopup.style.opacity = '1';
					loginPopup.style.visibility = 'visible';
				loginClose.addEventListener('click',() =>{
					
					body2.style.opacity = '0';
					body2.style.visibility = 'hidden';	
					loginPopup.classList.remove('details-zoom-in');	
					setTimeout(() => {
				        loginPopup.style.opacity = '0';
				        loginPopup.style.visibility = 'hidden';
				    }, 300);
				});
				body2.addEventListener('click',() =>{
					body2.style.opacity = '0';
					body2.style.visibility = 'hidden';	
					loginPopup.classList.remove('details-zoom-in');	
					setTimeout(() => {
				        loginPopup.style.opacity = '0';
				        loginPopup.style.visibility = 'hidden';
				    }, 300);	
				
				});
				
			}

				
				
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
								
							 }
					}
					});
					login.addEventListener('click', () => {
						if(input.value === ""){
							label[i].classList.add('danger');
							p[i].innerText ="Required";
							p[i].classList.add('required');
						}else if(p[0].innerText === "" && p[1].innerText === "" ){
							login.type = "submit";
						}
					
				})
					
				});
				
				const loginMsg = document.querySelector('.login-msg');
				if(loginMsg != null){
					setTimeout(() => {
						loginMsg.style.display = "none";
				    }, 3000);
				}
				
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
				
				const navbar = document.querySelector('.nav-toggle');
	const navLink = document.querySelector('.nav-links');
	
	navbar.addEventListener('click',() =>{
		navLink.classList.toggle('nav-links-active-top');
		
	});
const accordians = document.querySelectorAll('.accordian');
	
	accordians.forEach(accordian =>{
		const icon = accordian.querySelector('.icon');
	
		const content = accordian.querySelector('.acc-content');
		icon.classList.add('active');
		content.style.maxHeight = content.scrollHeight + 'px';
	
		accordian.addEventListener('click',() =>{
		
			
			if(icon.classList.contains('active')  ){
				icon.classList.remove('active');
				content.style.maxHeight = null;
			}
			
			else {
				icon.classList.add('active');
				content.style.maxHeight = content.scrollHeight + 'px';
			}
		})
	})

	
	const btnList = document.querySelectorAll('.btnS');
	
	btnList.forEach(btnEl => {
		btnEl.addEventListener('click',() =>{
			if(btnEl.classList.contains('special')){
				btnEl.classList.remove('special');
			}else{
			btnEl.classList.add('special');
			}
		})
	})


	
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
				setTimeout(function(){
					body.style.opacity = '0';
					body.style.visibility = 'hidden';	
					popup.classList.remove('popup-animation');										
				},5000);
					
			}
				
				function updateQuantity(card,buttonSize,buttonColor){
		
				const selectedProd = card.querySelector('.productDetails');
				
				if(selectedProd != null){
					const BtnQuantity = card.querySelector('.buttons-quantity');
					let	index = selectedProd.value.indexOf('%');
					const values = selectedProd.value.substring(index+1);
					const quantity = card.querySelector('.quantity');
					const prodValues = values.split('$');
					const resultArray = [];
					
					prodValues.forEach((val) =>{
					 const eachProdValues = val.split('&');
					  resultArray.push(eachProdValues);
				});
				console.log(resultArray + "rohan");
					if(buttonSize.value !== "0" && buttonColor[0].value !== ""){	
						let flag = false;
						resultArray.forEach((val)=>{
						if(val[0] === buttonSize.value && val[1] === buttonColor[0].value){
							quantity.innerText = val[2];
							flag = true;
							quantity.style.display = "block";
							BtnQuantity.value = quantity.innerText;
						}
					});
						if(flag === false){
							
							quantity.innerText = "0";
							BtnQuantity.value = "1";
						}
						
					}
				
					}
				}
				
				const cards = document.querySelectorAll('.card');

				cards.forEach((card) =>{
					const colorBtn = card.querySelectorAll('.colour span');
					const colorImg = card.querySelectorAll('.imgBox img');
					const sizebtn = card.querySelectorAll(' .size input ');
					const buttonSize = card.querySelector('.buttons .buttons-size');
					const buttonColor = card.querySelectorAll('.buttons-color');
					const msg = card.querySelector('.contentBox .danger');
					const msgBg = document.querySelector('.msg');
					const msg2 = document.querySelector('.danger1');
					const msgBorder = card.querySelector('.size .getsize');
					
			
					sizebtn.forEach((btn) =>{
						
						btn.addEventListener('click',() =>{
						sizebtn.forEach((btn) =>{
							btn.classList.remove('selectedSize');
						})
							if(msg != null && msgBorder != null){
							msg.innerText = "";
							msgBg.style.display = "none";
							msg2.innerText = "";
							msgBorder.style.border ="none";
						}
							buttonSize.value = btn.value;
							btn.classList.add('selectedSize');
							updateQuantity(card,buttonSize,buttonColor);
							 
							removeSize(card);
							
						})
					})
					buttonColor[0].value = colorBtn[0].classList.item(1);
					buttonColor[1].value = colorBtn[0].classList.item(1);
					colorBtn.forEach((btn,index) =>{
						
						btn.addEventListener('click',() =>{
							colorBtn.forEach((otherBtn) => {
								otherBtn.classList.remove('selected');
								});
							buttonColor[0].value = btn.classList.value;
							buttonColor[1].value = btn.classList.value;
							btn.classList.add('selected');
							colorImg.forEach((img) =>{
								img.style.display = 'none';
							});
							colorImg[index].style.display = 'block'; 
							updateQuantity(card,buttonSize,buttonColor);	
							removeSize(card);
							
							
						});
					});
		
					
				});
				
				function addSize() {
					 const cards = document.querySelectorAll('.card');
					  cards.forEach((card) => {
						  const btns = card.querySelectorAll('.btn');
						  const buttonSize = card.querySelector('.buttons .buttons-size');
						  const getSize = card.querySelector('.size .size-border');
						  const msg = card.querySelector('.contentBox .sizeMsg');
						  const quantity = card.querySelector('.quantity');
						
						  btns.forEach((btn,index)=>{
							 
						  btn.addEventListener('click', () => {  
							  console.log("kro ");
							  if(buttonSize.value === "0"){
								  getSize.classList.add('getsize');
								  msg.innerText = "Please select a size.";
								  msg.classList.add('danger');
							  }else{
								  if(index === 0 ){
									  decrement(card,quantity);
								  }else{
									  increment(card,quantity);
								  }
								  
							  }
						  });
						  });
						
					  });
				}
				addSize();
				function decrement(card,quantity){
				
					  const BtnQuantity = card.querySelector('.buttons-quantity');
					  const quantityMsg = card.querySelector('.quantityMsg');
							if(quantity === null){
									  if(quantityMsg.innerText === ''  ){
										  quantityMsg.innerText = ''; 
									  }else if(quantityMsg.innerText === '1' ){
										  quantityMsg.innerText = '1'; 
									  }
									  else{
										  quantityMsg.innerText--;
										  quantityMsg.classList.add('quantity');
										  BtnQuantity.value = quantityMsg.innerText;
									  }
							}else{
							  if(quantity.innerText === '1'  ){
								  quantity.innerText = '1';
								  BtnQuantity.value = quantity.innerText;
							  }else if(quantity.innerText === '0'){
								  quantity.innerText = '0';
								  
								  
							  }else{
							  quantity.innerText--;
							  BtnQuantity.value = quantity.innerText;
							  }
							 
							}
				
				}
				function increment(card,quantity){
					  const BtnQuantity = card.querySelector('.buttons-quantity');
					  const prodQuantity = card.querySelector('.prodQuantity');
					  const quantityMsg = card.querySelector('.quantityMsg');
					   
							if(quantity === null){
								quantityMsg.classList.add('quantity');
								  if(quantityMsg.innerText === prodQuantity.value){
									  quantityMsg.innerText = prodQuantity.value; 
									  
								  }else{
									  quantityMsg.innerText++;
								 
								  }
								  BtnQuantity.value = quantityMsg.innerText;
							}else{
								
							  if(quantity.innerText === prodQuantity.value){
								  quantity.innerText = prodQuantity.value; 
								  
							  }else{
							  quantity.innerText++;
							 
							  }
							  BtnQuantity.value = quantity.innerText;
							}
				
				}
				function removeSize(card){
					  const getSize = card.querySelector('.size .size-border');
					  const msg = card.querySelector('.contentBox .sizeMsg');
					getSize.classList.remove('getsize');
					  msg.innerText = "";
					  msg.classList.remove('danger');
					  
				}
				
				const dropdowns = document.querySelectorAll('.dropdown');
				console.log(dropdowns);
				dropdowns.forEach(dropdown =>{
					const select = dropdown.querySelector('.select');
					const caret = dropdown.querySelector('.caret');
					const menu = dropdown.querySelector('.menu');
					const options = dropdown.querySelectorAll('.menu input');
					const selected = dropdown.querySelector('.selected');
					const selected1 = dropdown.querySelector('.selected1');
					select.addEventListener('click',() =>{
						caret.classList.toggle('caret-rotate');
						if(select.classList.contains('active')  ){
							select.classList.remove('active');
							menu.style.maxHeight = null;
						}
						
						else {
							select.classList.add('active');
							menu.style.maxHeight = menu.scrollHeight + 'px';
						}
						
					})
					options.forEach(option =>{
						option.addEventListener('click',() =>{
						selected1.innerText = ": " + option.value;
							selected1.classList.add('active');
							select.classList.remove('active');
							caret.classList.remove('caret-rotate');
						menu.style.maxHeight = null;
							options.forEach(option =>{
								option.classList.remove('active');	
							})
							option.classList.add('active');
						})
					})
				})
			const hide = document.querySelector('.hide');
			const aside = document.querySelector('aside');	
			const cardSection = document.querySelector('.cards-section');
			const wall = document.querySelector('.wall');
			const footer = document.querySelector('.footer');
			const sort = document.querySelector('.sort');
			console.log(sort);
			if(aside.offsetHeight > cardSection.offsetHeight){
				aside.style.height = '65vh';
			}
				hide.addEventListener('click',() =>{
					wall.classList.toggle('container-zoom');
					aside.classList.toggle('aside-hide');
					if(wall.classList.contains('container-zoom')){
						let a = wall.offsetHeight * 1.2 ;
						footer.style.top = (a - wall.offsetHeight)   + 'px';
					}else{
						console.log("else "+wall.offsetHeight);
						footer.style.top = null;
					}
				})
			
				const body1 = document.querySelector('.body');
				const loginPopup = document.querySelector('.login-popup');
				const loginClose = document.querySelector('.info i');
					if(loginPopup !=null){
						body1.style.opacity = '1';
						body1.style.visibility = 'visible';
					 	loginPopup.classList.add('details-zoom-in');
					 	loginPopup.style.opacity = '1';
						loginPopup.style.visibility = 'visible';
					loginClose.addEventListener('click',() =>{
						
						body1.style.opacity = '0';
						body1.style.visibility = 'hidden';	
						loginPopup.classList.remove('details-zoom-in');	
						setTimeout(() => {
					        loginPopup.style.opacity = '0';
					        loginPopup.style.visibility = 'hidden';
					    }, 300);
					});
					body1.addEventListener('click',() =>{
						body1.style.opacity = '0';
						body1.style.visibility = 'hidden';	
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
					const sortHide = document.querySelector('.sort');
					let scroll = 0;
					window.addEventListener('scroll',() =>{
						console.log(window.scrollY);
						if(window.scrollY > scroll){
							nav.classList.add('scrolled');
							sort.style.top = "0";
						}else{
							nav.classList.remove('scrolled');
							sort.style.top = "80px";
						}
					scroll = window.scrollY;
					})
	
	
					const navbar = document.querySelector('.nav-toggle');
	const navLink = document.querySelector('.nav-links');
	
	navbar.addEventListener('click',() =>{
		navLink.classList.toggle('nav-links-active-top');
		
	});
				
		const showFilter = document.querySelector('.show-wrapper');
	const asideShow = document.querySelector('aside');
	const asideClose = document.querySelector('.aside-close');
	
	showFilter.addEventListener('click',() =>{
		asideShow.classList.add('show-filter');
		
		asideClose.addEventListener('click',() =>{	
			asideShow.classList.remove('show-filter');		
			});
	})
			
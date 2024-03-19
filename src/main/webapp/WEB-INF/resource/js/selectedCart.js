
			const plus = document.querySelectorAll(".plus");
			const minus = document.querySelectorAll(".minus");
			const num = document.querySelectorAll(".num");
			const total = document.querySelectorAll(".total-input");
			const price = document.querySelectorAll(".price");
			const subtotal = document.querySelector(".subtotal-input");
			const alltotal = document.querySelector(".alltotal");
			const totalInput = document.querySelectorAll(".total-input-p");
			const subtotalP = document.querySelector(".subtotal-input-p");
			const alltotalP = document.querySelector(".alltotal-p");
			const quantityWishlist = document.querySelectorAll(".quantity-wishlist");
			const updateBtn = document.querySelector('.update-btn-input');
			let sbtotal = 0;
			sbtotal = parseInt(subtotal.value);
			plus.forEach((plusbtn,index) =>{
				plusbtn.addEventListener("click",()=>{
					updateBtn.classList.add('update-btn-active');
					updateBtn.type = "submit";
					let	a=num[index];
					let v = a.value;
					v++;
					a.value = v;
					quantityWishlist[index].value = a.value;
					
				total[index].value = price[index].value * v ;
				
				totalInput[index].innerHTML = total[index].value + ".00" ;
				
				sbtotal += parseInt(price[index].value) ;
				subtotal.value = sbtotal;
				subtotalP.innerHTML =  subtotal.value + ".00";
				alltotal.value = sbtotal + 1250;
				alltotalP.innerHTML = alltotal.value + ".00" ;
				
				});
			});
			
			minus.forEach((minusbtn,index) =>{
				minusbtn.addEventListener("click",()=>{
					updateBtn.classList.add('update-btn-active');
					updateBtn.type = "submit";
					let	a=num[index];
					let v = a.value;
					v--;
					total[index].value = total[index].value - price[index].value   ;
					totalInput[index].innerHTML = total[index].value + ".00" ;
					if(v<1){
						v = 1;
						total[index].value = price[index].value;
						totalInput[index].innerHTML = total[index].value ;
						sbtotal += parseInt(price[index].value)
					}
					sbtotal -= parseInt(price[index].value) ;
					subtotal.value = sbtotal;
					subtotalP.innerHTML =  subtotal.value + ".00";
					a.value = v;
					quantityWishlist[index].value = a.value;
					alltotal.value = sbtotal + 1250;
					alltotalP.innerHTML = alltotal.value + ".00" ;
					
					
				});
			});
			
			const info = document.querySelector(".subtotal-info");
			const text = document.querySelector(".subtotal-text");
			info.addEventListener('click',()=>{
				if(text.classList.contains('text')){
					text.classList.remove('text');
					text.innerText = "";
				}else{
					text.classList.add('text');
					text.innerText = "The subtotal reflects the total price of your order, including duties and taxes, before any applicable discounts. It does not include delivery costs and international transaction fees.";
				}
								
			});
			
				const sizeWishlist = document.querySelectorAll(".size-wishlist");
				const bagText  = document.querySelectorAll('.bag-text');
				bagText.forEach((e,index)=>{
					const sizeSelect = e.querySelector('.size-wrapper-select');
					const anchor = e.querySelector(".anchor");
					const preSizeSelectValue = sizeSelect.value;
					console.log(preSizeSelectValue);
					sizeSelect.addEventListener('click',()=>{
						updateBtn.classList.add('update-btn-active');
						updateBtn.type = "submit";
						sizeWishlist[index].value = sizeSelect.value ;
						console.log(anchor.href);
						let replaceUrl = anchor.href.replace("size="+preSizeSelectValue,"size="+sizeWishlist[index].value);
						anchor.href = replaceUrl;
						console.log(anchor.href);
					})
				})
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
				
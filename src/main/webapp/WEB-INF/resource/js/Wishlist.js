const buttons = document.querySelectorAll('.arrow i');
		const productContainer = document.querySelector('.more-products-content');
		const imgCard = document.querySelector('.more-products');
		const width = document.querySelector('.more-products-content').offsetWidth;

		buttons.forEach(button =>{
			button.addEventListener('click',() =>{
				if(button.id ==="left"){
					imgCard.scrollLeft += -width -10;
				
				}else{
					imgCard.scrollLeft += width + 10;
				}
			});
			
		});
		
		const body = document.querySelector('.overlay');
		const popup = document.querySelector('.popup');
		const btns = document.querySelectorAll('.btn .sizeNot');
		const selectedsize = document.querySelector('.selectedsize');
		const addtoBag = document.querySelector('.wishBag');
		const selectSizetext = document.querySelector('.popup-text-selectsize p');
		const selectSizeBorder = document.querySelector('.btn');

			if(popup != null){
				
						
				
			body.style.display = 'block';
			body.addEventListener('click',() =>{
				body.style.display = 'none';
				popup.style.display = 'none';
			});

			btns.forEach(btn => {
				btn.addEventListener('click',() =>{
					

				btns.forEach(btn =>{
					btn.classList.remove('btnActive');
				})
			
					btn.classList.add('btnActive');
					selectedsize.value = btn.value;

					if(selectedsize.value !== ""){
						selectSizetext.classList.remove('selectSize');
						selectSizeBorder.classList.remove('getSize');
						addtoBag.type = "submit";
					}
					
				});	
				
				addtoBag.addEventListener('click',()=>{
					if(selectedsize.value === ""){
						selectSizetext.classList.add('selectSize');
						selectSizeBorder.classList.add('getSize');
					}
		
				})
			});
			
			
		}
			
			const body1 = document.querySelector('.overlay');
			const popup1 = document.querySelector('.popup1');
				if(popup1 !=null){
					body1.style.display = 'block';	
					body1.addEventListener('click',() =>{
						body1.style.display = 'none';	
						  popup1.style.display = 'none';	
					
					});
				};
				
			const edit = document.querySelector('.edit-btn-input');	
			const WishlistContent = document.querySelectorAll('.wishlist-content-items');
			const wishlistIds = document.querySelector('.wishlistIds');
			const editDiv = document.querySelector('.wishlist-edit');
			const removeIds = [];
			edit.addEventListener('click',() =>{

				const submit = document.createElement("input");
				submit.type = "submit";
				submit.value = "Done";
				submit.className = "edit-btn-input";
				submit.classList.add('submitEdit');
				editDiv.appendChild(submit);
				WishlistContent.forEach((i)=>{
					const icon = i.querySelector('i');
					const iconDiv = i.querySelector('.icon-bg');
					const inputId = i.querySelector('input');
					const imgOverlay = i.querySelector('.img-overlay');
					iconDiv.style.display = "flex";
					
					icon.addEventListener('click',()=>{
						if(icon.classList.contains('fa-regular')){
							icon.classList.remove('fa-regular');	
							icon.classList.add('fa-solid');
							imgOverlay.classList.remove('img-overlay-active');
						const index = removeIds.indexOf(inputId.value);
						removeIds.splice(index,1);
					
						}else{
							icon.classList.remove('fa-solid');
							icon.classList.add('fa-regular');
							imgOverlay.classList.add('img-overlay-active');
							removeIds.push(inputId.value);
						
						}
						console.log(removeIds);
						let allIds = "";
						removeIds.forEach((i) =>{
							wishlistIds.value = "";
							if(allIds !== ""){
								allIds += "&";
							}
							 allIds += i;
						})
						wishlistIds.value = allIds;
						
					})
				})
				
			})
			
				const nav = document.querySelector('.main-nav');
					const wishlistBar = document.querySelector('.wishlist-heading-place');
					let scroll = 0;
					window.addEventListener('scroll',() =>{
				
						if(window.scrollY > scroll){
							nav.classList.add('scrolled');
							wishlistBar.style.top = "20px";
						}else{
							nav.classList.remove('scrolled');
							wishlistBar.style.top = "80px";
						}
					scroll = window.scrollY;
					})
					
					const navbar = document.querySelector('.nav-toggle');
	const navLink = document.querySelector('.nav-links');
	
	navbar.addEventListener('click',() =>{
		navLink.classList.toggle('nav-links-active-top');
		
	});
	
	const popupSize = document.querySelector('.popup');
			if(popupSize != null){
			popupSize.style.opacity ='1';
			popupSize.style.transform = 'translate(-50%,-50%) scale(1)';
			}
	
			
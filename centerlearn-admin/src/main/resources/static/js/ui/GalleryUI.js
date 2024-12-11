import util from "../utils.js";

const galleryUI = {
    el: {
        editModal: $("#edit-modal .modal"),
        idEl: $("#image-detail"),
        imageEl: $("#image-detail img"),
        nameEl: $("#edit-image-name"),
        createEl: $("#image-createdAt"),
        updateEl: $("#image-updatedAt"),
        capacityEl: $("#image-capacity"),
        sizeEl: $("#image-size"),
    },
    renderEditModal: (gallery) => {
        galleryUI.el.imageEl.attr('src', gallery.url);
        galleryUI.el.idEl.attr('data-id', gallery.id);
        galleryUI.el.idEl.data('id', gallery.id);
        galleryUI.el.nameEl.val(gallery.name);
        galleryUI.el.createEl.text(util.formatDate(gallery.createdAt));
        galleryUI.el.updateEl.text(util.formatDate(gallery.updatedAt));
        galleryUI.el.capacityEl.text((gallery.fileSize / 1048576).toFixed(2) + " MB");
        galleryUI.el.editModal.modal('show');
        galleryUI.el.imageEl.on('load', function() {
            const naturalWidth = this.naturalWidth;
            const naturalHeight = this.naturalHeight;
            galleryUI.el.sizeEl.text(naturalWidth + " x " + naturalHeight);
        })
    },
    closeEditModal: () => {
        galleryUI.el.imageEl.attr('src', '');
        galleryUI.el.idEl.attr('data-id', '');
        galleryUI.el.idEl.data('id', '');
        galleryUI.el.nameEl.text('');
        galleryUI.el.createEl.text('');
        galleryUI.el.updateEl.text('');
        galleryUI.el.capacityEl.text('');
    },
    renderGallery: (galleries) => {
        const container = $(".list-media-container");
        container.empty();
        if (galleries.length === 0) {
            container.append(`<div>Trống<div>`);
            return;
        }
        galleries.forEach(function(gallery) {
            let item = `
                <div class="col-lg-2 fade-in media-item">
                    <a href="#" data-id="${gallery.id != null && gallery.id !== undefined ? gallery.id : ''}" class="text-decoration-none">
                        <div class="image">
                            <img src="${gallery.url}" alt="Media Image">
                        </div>
                        <div class="content">
                            <span>${gallery.name}</span>
                        </div>
                    </a>
                    <i class="delete-btn fa-solid fa-delete-left"></i>
                </div>
            `
            container.append(item);
        })
        setTimeout(function() {
            container.find('.fade-in').addClass('show');
        }, 100);
    }
};

export default galleryUI;
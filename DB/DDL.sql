create table admin_upload_video(
    id  int not null PRIMARY KEY AUTO_INCREMENT,
    wechat_code varchar(255),
    video_id int,
    video_img_id int,
    update_time datetime,
    user_download_count int);
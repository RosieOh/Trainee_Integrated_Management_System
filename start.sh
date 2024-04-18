# 데이터베이스 접속 정보 설정
DB_USER="root"
DB_PASSWORD="chunjae@12"
DB_NAME="chunjaeitlms"

# 실행할 쿼리 정의
QUERY="GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'chunjae@12' WITH GRANT OPTION; FLUSH PRIVILEGES;"

# 쿼리 실행
mysql -u "$DB_USER" -p"$DB_PASSWORD" "$DB_NAME" -e "$QUERY"

echo "권한이 성공적으로 부여되었습니다."